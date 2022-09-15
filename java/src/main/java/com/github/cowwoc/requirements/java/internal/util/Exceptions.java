/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.GlobalRequirements;
import com.github.cowwoc.requirements.java.internal.diff.ContextLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
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

	/**
	 * Constructs an exception.
	 *
	 * @param <E> the type of the exception
	 */
	@FunctionalInterface
	private interface ExceptionBuilder<E>
	{
		/**
		 * Constructs an exception.
		 *
		 * @param exceptions an instance of {@code Exceptions}
		 * @param message    the detail message. The detail message is saved for later retrieval by the
		 *                   {@code getMessage()} method
		 * @param cause      the cause (which is saved for later retrieval by the {@code getCause()} method).
		 *                   (A {@code null} value is permitted, and indicates that the cause is nonexistent or
		 *                   unknown.)
		 * @return a new instance of the exception
		 */
		E apply(Exceptions exceptions, String message, Throwable cause);
	}

	/**
	 * Constructs an optimized exception with a "message" parameter.
	 *
	 * @param <E> the type of the exception
	 */
	@FunctionalInterface
	private interface OptimizedExceptionWithMessage<E>
	{
		/**
		 * Constructs an exception.
		 *
		 * @param exceptions an instance of {@code Exceptions}
		 * @param message    the detail message. The detail message is saved for later retrieval by the
		 *                   {@code getMessage()} method
		 * @return a new instance of the exception
		 */
		E apply(Exceptions exceptions, String message);
	}

	/**
	 * Constructs an optimized exception with "message" and "cause" parameters.
	 *
	 * @param <E> the type of the exception
	 */
	@FunctionalInterface
	private interface OptimizedExceptionWithMessageAndCause<E> extends ExceptionBuilder<E>
	{
	}

	/**
	 * Constructs a regular exception with a "message" parameter.
	 *
	 * @param <E> the type of the exception
	 */
	@FunctionalInterface
	private interface RegularExceptionWithMessage<E>
	{
		/**
		 * Constructs an exception.
		 *
		 * @param message the detail message. The detail message is saved for later retrieval by the
		 *                {@code getMessage()} method
		 * @return a new instance of the exception
		 */
		E apply(String message);
	}

	/**
	 * Constructs a regular exception with "message" and "cause" parameters.
	 *
	 * @param <E> the type of the exception
	 */
	@FunctionalInterface
	private interface RegularExceptionWithMessageAndCause<E>
	{
		/**
		 * Constructs an exception.
		 *
		 * @param message the detail message. The detail message is saved for later retrieval by the
		 *                {@code getMessage()} method
		 * @param cause   the cause (which is saved for later retrieval by the {@code getCause()} method).
		 *                (A {@code null} value is permitted, and indicates that the cause is nonexistent or
		 *                unknown.)
		 * @return a new instance of the exception
		 */
		E apply(String message, Throwable cause);
	}

	private record CacheKey(Class<?> type, boolean tryToOptimize)
	{
	}

	private final Function<CacheKey, ExceptionBuilder<?>> computeExceptionBuilder =
		key ->
		{
			try
			{
				MethodHandle exceptionConstructor;
				MethodType factoryType;

				Class<?> optimizedType;
				if (key.tryToOptimize)
					optimizedType = getOptimizedException(key.type).orElse(null);
				else
					optimizedType = null;
				boolean exceptionSupportsCause = supportsMessageAndCause(key.type);

				if (optimizedType == null)
				{
					MethodType constructorType;
					if (exceptionSupportsCause)
					{
						factoryType = MethodType.methodType(RegularExceptionWithMessageAndCause.class);
						constructorType = MethodType.methodType(void.class, String.class, Throwable.class);
					}
					else
					{
						factoryType = MethodType.methodType(RegularExceptionWithMessage.class);
						constructorType = MethodType.methodType(void.class, String.class);
					}
					exceptionConstructor = lookup.findConstructor(key.type, constructorType);
				}
				else
				{
					MethodType constructorType;
					if (exceptionSupportsCause)
					{
						factoryType = MethodType.methodType(OptimizedExceptionWithMessageAndCause.class);
						constructorType = MethodType.methodType(void.class, Exceptions.class, String.class,
							Throwable.class);
					}
					else
					{
						factoryType = MethodType.methodType(OptimizedExceptionWithMessage.class);
						constructorType = MethodType.methodType(void.class, Exceptions.class, String.class);
					}
					exceptionConstructor = lookup.findConstructor(optimizedType, constructorType);
				}
				return getExceptionBuilder(exceptionConstructor, factoryType, exceptionSupportsCause);
			}
			catch (Throwable e)
			{
				throw WrappingException.wrap(e);
			}
		};
	private final ConcurrentMap<CacheKey, ExceptionBuilder<?>> classToExceptionBuilder =
		new ConcurrentHashMap<>((int) Math.ceil(3 / 0.75));
	private final Logger log = LoggerFactory.getLogger(Exceptions.class);

	/**
	 * Creates a new instance.
	 */
	public Exceptions()
	{
	}

	/**
	 * @param type an exception type
	 * @return true if the exception accepts a {@code (message, cause)} signature
	 */
	private boolean supportsMessageAndCause(Class<?> type)
	{
		for (Constructor<?> constructor : type.getDeclaredConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length != 2)
				continue;
			if (parameterTypes[0] == String.class && parameterTypes[1] == Throwable.class)
				return true;
		}
		return false;
	}

	private <T extends Throwable> ExceptionBuilder<T> getExceptionBuilder(
		MethodHandle exceptionConstructor, MethodType factoryType, boolean supportsCause) throws Throwable
	{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle target = LambdaMetafactory.metafactory(
			lookup,
			"apply",
			factoryType,
			// https://stackoverflow.com/a/73749785/14731: Only type parameters have to be "erased" to "Object"
			exceptionConstructor.type().changeReturnType(Object.class),
			exceptionConstructor,
			exceptionConstructor.type()
		).getTarget();

		Class<?> exceptionType = factoryType.returnType();
		if (exceptionType.isAssignableFrom(RegularExceptionWithMessage.class))
		{
			return (exceptions, message, cause) ->
			{
				try
				{
					RegularExceptionWithMessage<?> exceptionBuilderFactory =
						(RegularExceptionWithMessage<?>) target.invokeExact();
					@SuppressWarnings("unchecked")
					T exception = (T) exceptionBuilderFactory.apply(message);
					@SuppressWarnings("unchecked")
					T exceptionBuilder = (T) exception.initCause(cause);
					return exceptionBuilder;
				}
				catch (Throwable e)
				{
					throw WrappingException.wrap(e);
				}
			};
		}
		if (exceptionType.isAssignableFrom(RegularExceptionWithMessageAndCause.class))
		{
			return (exceptions, message, cause) ->
			{
				try
				{
					RegularExceptionWithMessageAndCause<?> exceptionBuilderFactory =
						(RegularExceptionWithMessageAndCause<?>) target.invokeExact();
					@SuppressWarnings("unchecked")
					T exceptionBuilder = (T) exceptionBuilderFactory.apply(message, cause);
					return exceptionBuilder;
				}
				catch (Throwable e)
				{
					throw WrappingException.wrap(e);
				}
			};
		}
		MethodHandle methodHandle = target.asType(target.type().changeReturnType(ExceptionBuilder.class));

		if (!supportsCause)
		{
			return (exceptions, message, cause) ->
			{
				try
				{
					OptimizedExceptionWithMessage<?> exceptionBuilder =
						(OptimizedExceptionWithMessage<?>) target.invokeExact();
					@SuppressWarnings("unchecked")
					T result = (T) exceptionBuilder.apply(exceptions, message);
					return result;
				}
				catch (Throwable e)
				{
					throw WrappingException.wrap(e);
				}
			};
		}

		@SuppressWarnings("unchecked")
		ExceptionBuilder<T> result = (ExceptionBuilder<T>) methodHandle.invokeExact();
		return result;
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
		assert (type != null) : "type may not be null";
		try
		{
			// When we instantiate a new exception inside this method, we end up with:
			//
			// java.lang.IllegalArgumentException: message of top exception
			//   at com.github.cowwoc.requirements.java.internal.util.Exceptions.createException(Exceptions.java:76)
			//   at com.github.cowwoc.requirements.java.internal.util.ExceptionBuilder.build(ExceptionBuilder.java:179)
			//   at com.github.cowwoc.requirements.java.internal.impl.SomeVerifier.method(SomeVerifier.java:1)
			//   at UserCode.method1(UserCode.java:1)
			// Caused by: message of underlying exception
			//   at Cause.method1(Cause.java:1)
			//
			// If "cleanStackTrace" is true, we need to strip out the top 3 stack trace elements. But we are also
			// forced to strip out the exception cause because it was thrown inside this library.
			if (cleanStackTrace)
			{
				// Why? Read previous comment
				cause = null;
			}
			ExceptionBuilder<E> exceptionBuilder = getExceptionBuilder(type, cleanStackTrace);

			E result = exceptionBuilder.apply(this, message, cause);
			boolean isOptimized = isOptimizedException(result.getClass());
			if (!isOptimized && cleanStackTrace)
			{
				// We need to strip the stack trace eagerly because we don't have an optimized exception
				StackTraceElement[] stackTrace = result.getStackTrace();
				StackTraceElement[] newStackTrace = removeLibraryFromStackTrace(stackTrace);
				if (newStackTrace != stackTrace)
					result.setStackTrace(newStackTrace);
			}
			return result;
		}
		catch (Throwable e)
		{
			throw WrappingException.wrap(e);
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
	 * @param <T>             the type of the exception
	 * @param type            the type of the exception
	 * @param cleanStackTrace true if stack trace references to this library should be removed
	 * @return a factory used to construct the exception
	 */
	private <T extends Throwable> ExceptionBuilder<T> getExceptionBuilder(Class<T> type,
		boolean cleanStackTrace)
	{
		boolean tryToOptimize = cleanStackTrace;
		CacheKey key = new CacheKey(type, tryToOptimize);

		@SuppressWarnings("unchecked")
		ExceptionBuilder<T> exceptionBuilder = (ExceptionBuilder<T>) classToExceptionBuilder.
			computeIfAbsent(key, computeExceptionBuilder);
		return exceptionBuilder;
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
		Constructor<?>[] constructors = type.getDeclaredConstructors();
		if (constructors.length < 1)
			return false;
		Class<?>[] parameterTypes = constructors[0].getParameterTypes();
		if (parameterTypes.length < 1)
			return false;
		Class<?> firstParameter = parameterTypes[0];
		return firstParameter != null && firstParameter.isAssignableFrom(Exceptions.class);
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