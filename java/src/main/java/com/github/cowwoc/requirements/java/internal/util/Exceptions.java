/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import com.github.cowwoc.requirements.annotation.OptimizedException;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.GlobalRequirements;
import com.github.cowwoc.requirements.java.internal.diff.ContextLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Exception helper functions.
 */
public final class Exceptions
{
	private final Lookup lookup = MethodHandles.lookup();
	/**
	 * The package name of this library.
	 */
	private final String libraryPackage = getParentPackage(Exceptions.class.getPackage().getName(), 3);

	private final MethodType optimizedConstructorWithCause = MethodType.methodType(void.class,
		Exceptions.class, String.class, Throwable.class);
	private final MethodType optimizedConstructorWithoutCause = MethodType.methodType(void.class,
		Exceptions.class, String.class);

	private final MethodType nonOptimizedConstructorWithCause = MethodType.methodType(void.class, String.class,
		Throwable.class);
	private final MethodType nonOptimizedConstructorWithoutCause = MethodType.methodType(void.class,
		String.class);

	private record CacheKey(Class<?> type, boolean optimized, boolean withCause)
	{
	}

	private final Function<CacheKey, MethodHandle> computeConstructor =
		key ->
		{
			try
			{
				Class<?> optimizedType;
				if (key.optimized)
					optimizedType = getOptimizedException(key.type).orElse(null);
				else
					optimizedType = null;

				if (optimizedType == null)
				{
					if (key.withCause)
						return lookup.findConstructor(key.type, nonOptimizedConstructorWithCause);
					return lookup.findConstructor(key.type, nonOptimizedConstructorWithoutCause);
				}
				if (key.withCause)
					return lookup.findConstructor(optimizedType, optimizedConstructorWithCause);
				return lookup.findConstructor(optimizedType, optimizedConstructorWithoutCause);
			}
			catch (ReflectiveOperationException e)
			{
				throw new RuntimeException(e);
			}
		};
	private final ConcurrentMap<CacheKey, MethodHandle> classToConstructor = new ConcurrentHashMap<>(3);
	private final Logger log = LoggerFactory.getLogger(Exceptions.class);

	/**
	 * Creates a new instance.
	 */
	public Exceptions()
	{
	}

	/**
	 * @param name  the name of a Java package
	 * @param count the number of times to walk up the hierarchy
	 * @return the name of the parent of the package
	 * @throws AssertionError if {@code name} is null or has no parent. If {@code count} is negative.
	 */
	private String getParentPackage(String name, int count)
	{
		assert (name != null) : "name may not be null";
		assert (count > 0) : "count may not be negative or zero";
		assert (!name.isBlank()) : "name may not be blank";
		int index = name.lastIndexOf('.');
		if (index == -1)
			throw new AssertionError("pkg may not be the root package");
		String result = name.substring(0, index);
		if (count == 1)
			return result;
		return getParentPackage(result, count - 1);
	}

	/**
	 * Throws an exception with the specified message and cause.
	 *
	 * @param <E>             the type of the exception
	 * @param type            the type of the exception
	 * @param message         an explanation of what went wrong
	 * @param cause           the cause of the exception ({@code null} if absent)
	 * @param cleanStackTrace true if stack trace references to this library should be removed
	 * @return the exception
	 * @throws AssertionError if {@code type} is null
	 */
	@SuppressWarnings("LongLine")
	public <E extends Exception> E createException(Class<E> type, String message, Throwable cause,
		boolean cleanStackTrace)
	{
		// DESIGN: When we instantiate a new exception inside this method, we will end up with:
		//
		// java.lang.IllegalArgumentException: message of top exception
		//   at com.github.cowwoc.requirements.java.internal.util.Exceptions.createException(Exceptions.java:76)
		//   at com.github.cowwoc.requirements.java.internal.util.ExceptionBuilder.build(ExceptionBuilder.java:179)
		//   at com.github.cowwoc.requirements.java.internal.impl.SomeVerifier.method(SomeVerifier.java:1)
		//   at UserCode.method1(UserCode.java:1)
		// Caused by: message of underlying exception
		//   at Cause.method1(Cause.java:1)
		//
		// If cleanStackTrace is true, we need to strip out the top 3 stack trace elements. But we are also
		// forced to strip out the exception cause because it was thrown inside our API.
		assert (type != null) : "type may not be null";
		try
		{
			boolean withCause = !cleanStackTrace && cause != null;
			MethodHandle constructor = getConstructor(type, withCause, cleanStackTrace);
			boolean isOptimized = isOptimizedException(constructor.type().returnType());

			// invokeExact() fails unless we provide exact type specifications. Per
			// https://stackoverflow.com/a/27279268/14731 invokeExact() assumes that the method return type
			// is equal to the compile-time cast applied to it, unless we invoke changeReturnType() as seen below.
			// If we were to skip changeReturnType() and cast the return value to "E" then invokeExact() would
			// assume the return type is "Exception". This would fail because the real value
			// of "E" is only known at runtime and is rarely equal to "Exception". Therefore, we are forced to
			// cast the return type to "Exception" then back to "E" at runtime.
			constructor = constructor.asType(constructor.type().changeReturnType(Exception.class));

			E result;
			if (isOptimized)
			{
				if (withCause)
				{
					//noinspection unchecked
					result = (E) constructor.invokeExact(this, message, cause);
				}
				else
				{
					//noinspection unchecked
					result = (E) constructor.invokeExact(this, message);
				}
			}
			else
			{
				if (withCause)
				{
					//noinspection unchecked
					result = (E) constructor.invokeExact(message, cause);
				}
				else
				{
					//noinspection unchecked
					result = (E) constructor.invokeExact(message);
				}
				if (cleanStackTrace)
				{
					// We need to strip the stack trace eagerly because we don't have an optimized exception
					StackTraceElement[] stackTrace = result.getStackTrace();
					StackTraceElement[] newStackTrace = removeLibraryFromStackTrace(stackTrace);
					if (newStackTrace != stackTrace)
						result.setStackTrace(newStackTrace);
				}
			}
			return result;
		}
		catch (Throwable e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * @param type the type of the exception
	 * @return the type of the optimized exception
	 */
	private Optional<Class<?>> getOptimizedException(Class<?> type)
	{
		String exceptionProxy = libraryPackage + ".exception." + type.getName();
		try
		{
			// Instantiate a proxy that filters the stack trace lazily
			Class<?> result = Class.forName(exceptionProxy);
			log.debug("Found optimized exception: {}", exceptionProxy);
			return Optional.of(result);
		}
		catch (ClassNotFoundException e)
		{
			// Instantiate the original exception
			log.debug("Failed to load \"" + exceptionProxy + ".\n" +
				"\n" +
				"Relevant System Properties\n" +
				"--------------------------\n" +
				"java.class.path=" + System.getProperty("java.class.path") + "\n" +
				"user.dir=" + System.getProperty("user.dir"), e);
			return Optional.empty();
		}
	}

	/**
	 * @param type            the type of the exception
	 * @param withCause       true if the constructor takes an exception cause
	 * @param cleanStackTrace true if stack trace references to this library should be removed
	 * @return the constructor of the exception
	 * @throws Throwable if an error occurs
	 */
	private MethodHandle getConstructor(Class<?> type, boolean withCause, boolean cleanStackTrace)
		throws Throwable
	{
		try
		{
			boolean optimized = cleanStackTrace;
			CacheKey key = new CacheKey(type, optimized, withCause);
			return classToConstructor.computeIfAbsent(key, theKey -> computeConstructor.apply(theKey));
		}
		catch (RuntimeException e)
		{
			if (e.getCause() instanceof ReflectiveOperationException)
				throw e.getCause();
			throw e;
		}
	}

	/**
	 * Removes references to this library from an exception stack trace.
	 *
	 * @param elements the stack trace elements to process
	 * @return the updated stack trace elements
	 * @throws NullPointerException if {@code elements} are null
	 */
	public StackTraceElement[] removeLibraryFromStackTrace(StackTraceElement[] elements)
	{
		return filterStackTrace(elements, element ->
		{
			String className = element.getClassName();
			return className.startsWith(libraryPackage);
		});
	}

	/**
	 * Runs a predicate against all lines of a stack trace removing lines at or above those that match.
	 * <p>
	 * This method can be used to remove lines that hold little value for the end user (such as
	 * internal methods invoked by this library).
	 *
	 * @param elements      the stack trace elements to process
	 * @param elementFilter returns true if the current stack trace element and methods it invokes should be
	 *                      removed
	 * @return the updated stack trace elements
	 * @throws NullPointerException if any of the arguments are null
	 */
	private StackTraceElement[] filterStackTrace(StackTraceElement[] elements,
		Predicate<StackTraceElement> elementFilter)
	{
		int i = elements.length - 1;
		while (true)
		{
			StackTraceElement element = elements[i];
			if (elementFilter.test(element))
				break;
			if (i == 0)
				return elements;
			--i;
		}
		return Arrays.copyOfRange(elements, i + 1, elements.length);
	}

	/**
	 * Indicates if the specified type is an optimized exception.
	 * <p>
	 * The purpose of optimized exceptions is to defer {@link GlobalRequirements#withCleanStackTrace()}
	 * cleaning stack traces) until the stack trace is actually needed. If {@code isCleanStackTrace()} is
	 * false, no attempt is made to use optimized exceptions.
	 *
	 * @param type a class
	 * @return true if the class is an optimized exception
	 */
	public boolean isOptimizedException(Class<?> type)
	{
		return type.getDeclaredAnnotation(OptimizedException.class) != null;
	}

	/**
	 * Returns the requirements contextual information.
	 *
	 * @param threadContext          the thread context
	 * @param validatorConfiguration the validator configuration
	 * @param message                the exception message ({@code null} if absent)
	 * @param exceptionContext       the failure-specific context
	 * @return the requirements contextual information
	 * @throws NullPointerException if any of the arguments are null
	 */
	public String getContextMessage(Map<String, Object> threadContext, Configuration validatorConfiguration,
		String message, List<ContextLine> exceptionContext)
	{
		Map<String, Object> validatorContext = validatorConfiguration.getContext();

		List<ContextLine> mergedContext = new ArrayList<>(exceptionContext.size() +
			threadContext.size() + validatorContext.size());
		Set<String> existingKeys = new HashSet<>();
		for (ContextLine entry : exceptionContext)
		{
			if (entry.wasConvertedToString())
				mergedContext.add(entry);
			else
			{
				mergedContext.add(new ContextLine(entry.getName(), validatorConfiguration.toString(entry.getValue()),
					true));
			}
			String key = entry.getName();
			if (!key.isBlank())
				existingKeys.add(key);
		}

		for (Entry<String, Object> entry : validatorContext.entrySet())
		{
			if (existingKeys.add(entry.getKey()))
			{
				mergedContext.add(new ContextLine(entry.getKey(), validatorConfiguration.toString(entry.getValue()),
					true));
			}
		}

		for (Entry<String, Object> entry : threadContext.entrySet())
		{
			if (existingKeys.add(entry.getKey()))
			{
				mergedContext.add(new ContextLine(entry.getKey(), validatorConfiguration.toString(entry.getValue()),
					true));
			}
		}

		int maxKeyLength = 0;
		for (ContextLine entry : mergedContext)
		{
			String key = entry.getName();
			if (key.isBlank())
				continue;
			int length = key.length();
			if (length > maxKeyLength)
				maxKeyLength = length;
		}

		StringJoiner messageWithContext = new StringJoiner("\n");
		if (message != null)
			messageWithContext.add(message);
		StringBuilder line = new StringBuilder();
		for (ContextLine entry : mergedContext)
		{
			line.delete(0, line.length());
			String key = entry.getName();
			if (!key.isBlank())
				line.append(Strings.alignLeft(key, maxKeyLength) + ": ");
			line.append(entry.getValue());
			messageWithContext.add(line.toString());
		}
		return messageWithContext.toString();
	}
}