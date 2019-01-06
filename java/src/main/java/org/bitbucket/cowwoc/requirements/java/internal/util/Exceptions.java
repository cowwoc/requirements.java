/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import org.bitbucket.cowwoc.requirements.java.annotations.OptimizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
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
	private final MethodType constructorWithCause = MethodType.methodType(void.class, String.class, Throwable.class);
	private final MethodType constructorWithoutCause = MethodType.methodType(void.class, String.class);
	private final MethodType optimizedConstructorWithCause = MethodType.methodType(void.class, Exceptions.class, String.class, Throwable.class);
	private final MethodType optimizedConstructorWithoutCause = MethodType.methodType(void.class, Exceptions.class, String.class);
	private final BiFunction<Class<?>, Boolean, MethodHandle> computeConstructorWithCause = (clazz, removeLibraryFromStackTrace) ->
	{
		try
		{
			if (removeLibraryFromStackTrace)
			{
				Class<?> optimizedException = getOptimizedException(clazz).orElse(null);
				if (optimizedException != null)
					return lookup.findConstructor(optimizedException, optimizedConstructorWithCause);
			}
			return lookup.findConstructor(clazz, constructorWithCause);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	};
	private final BiFunction<Class<?>, Boolean, MethodHandle> computeConstructorWithoutCause = (clazz, removeLibraryFromStackTrace) ->
	{
		try
		{
			if (removeLibraryFromStackTrace)
			{
				Class<?> optimizedException = getOptimizedException(clazz).orElse(null);
				if (optimizedException != null)
					return lookup.findConstructor(optimizedException, optimizedConstructorWithoutCause);
			}
			return lookup.findConstructor(clazz, constructorWithoutCause);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	};
	private final ConcurrentMap<Class<?>, MethodHandle> classToConstructorsWithoutCause =
		new ConcurrentHashMap<>(3);
	private final ConcurrentMap<Class<?>, MethodHandle> classToConstructorsWithCause =
		new ConcurrentHashMap<>(3);
	private final Logger log = LoggerFactory.getLogger(Exceptions.class);

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
		assert (!name.trim().isEmpty()) : "name may not be empty";
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
	 * @param <E>                         the type of the exception
	 * @param type                        the type of the exception
	 * @param message                     an explanation of what went wrong
	 * @param cause                       the cause of the exception ({@code null} if absent)
	 * @param removeLibraryFromStackTrace true if exceptions should remove references to this library from their stack traces
	 * @return the exception
	 * @throws AssertionError if {@code type} is null
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message, Throwable cause,
	                                                                     boolean removeLibraryFromStackTrace)
	{
		// DESIGN: When we instantiate a new exception inside this method, we will end up with:
		//
		// java.lang.IllegalArgumentException: message of top exception
		//   at org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions.createException(Exceptions.java:76)
		//   at org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder.build(ExceptionBuilder.java:179)
		//   at org.bitbucket.cowwoc.requirements.java.internal.impl.SomeVerifier.method(SomeVerifier.java:1)
		//   at UserCode.method1(UserCode.java:1)
		// Caused by: message of underlying exception
		//   at Cause.method1(Cause.java:1)
		//
		// If removeLibraryFromstackTrace is true, we need to strip out the top 3 stack trace elements. But we are also forced to strip out
		// the exception cause because it was thrown inside our API.
		assert (type != null) : "type may not be null";
		try
		{
			boolean withCause = !removeLibraryFromStackTrace && cause != null;
			MethodHandle constructor = getConstructor(type, withCause, removeLibraryFromStackTrace);
			boolean isOptimized = isOptimizedException(constructor.type().returnType());
			// Convert the exception type to RuntimeException
			constructor = constructor.asType(constructor.type().changeReturnType(RuntimeException.class));

			RuntimeException result;
			if (isOptimized)
			{
				if (withCause)
					result = (RuntimeException) constructor.invokeExact(this, message, cause);
				else
					result = (RuntimeException) constructor.invokeExact(this, message);
			}
			else
			{
				if (withCause)
					result = (RuntimeException) constructor.invokeExact(message, cause);
				else
					result = (RuntimeException) constructor.invokeExact(message);
				if (removeLibraryFromStackTrace)
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
		String exceptionProxy = libraryPackage + ".exception." + type.getName() + "Wrapper";
		try
		{
			// Instantiate a proxy that filters the stack trace lazily
			Class<?> result = Class.forName(exceptionProxy);
			log.debug("Found optimized exception: {}", exceptionProxy);
			return Optional.of(result);
		}
		catch (ClassNotFoundException unused)
		{
			// Instantiate the original exception
			log.debug("Could not find optimized exception: {}", exceptionProxy);
			return Optional.empty();
		}
	}

	/**
	 * @param type                        the type of the exception
	 * @param withCause                   true if the constructor takes an exception cause
	 * @param removeLibraryFromStackTrace true if exceptions should remove references to this library from their stack traces
	 * @return the constructor of the exception
	 * @throws Throwable if an error occurs
	 */
	private MethodHandle getConstructor(Class<?> type, boolean withCause, boolean removeLibraryFromStackTrace) throws Throwable
	{
		try
		{
			if (withCause)
			{
				return classToConstructorsWithCause.computeIfAbsent(type, clazz ->
					computeConstructorWithCause.apply(clazz, removeLibraryFromStackTrace));
			}
			return classToConstructorsWithoutCause.computeIfAbsent(type, clazz ->
				computeConstructorWithoutCause.apply(clazz, removeLibraryFromStackTrace));
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
			return className.startsWith(libraryPackage) && !className.endsWith("Test");
		});
	}

	/**
	 * Runs a predicate against all lines of a stack trace removing lines at or above those that match.
	 * <p>
	 * This method can be used to remove lines that hold little value for the end user (such as
	 * internal methods invoked by this library).
	 *
	 * @param elements      the stack trace elements to process
	 * @param elementFilter returns true if the current stack trace element and methods it invokes should be removed
	 * @return the updated stack trace elements
	 * @throws NullPointerException if any of the arguments are null
	 */
	private StackTraceElement[] filterStackTrace(StackTraceElement[] elements, Predicate<StackTraceElement> elementFilter)
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
	 * @param type a class
	 * @return true if the class is an optimized exception
	 */
	public boolean isOptimizedException(Class<?> type)
	{
		return type.getDeclaredAnnotation(OptimizedException.class) != null;
	}
}
