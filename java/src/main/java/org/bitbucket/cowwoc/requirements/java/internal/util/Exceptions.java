/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Arrays;
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
	private final MethodType constructorWithCause = MethodType.methodType(void.class, String.class, Throwable.class);
	private final MethodType constructorWithoutCause = MethodType.methodType(void.class, String.class);
	private final Function<Class<?>, MethodHandle> computeConstructorWithCause = c ->
	{
		try
		{
			return lookup.findConstructor(c, constructorWithCause);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	};
	private final Function<Class<?>, MethodHandle> computeConstructorWithoutCause = c ->
	{
		try
		{
			return lookup.findConstructor(c, constructorWithoutCause);
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

	/**
	 * @param name  the name of a Java package
	 * @param count the number of times to walk up the hierarchy
	 * @return the name of the parent of the package
	 * @throws AssertionError if {@code name} is null or has no parent; if {@code count} is negative
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
	 * @param <E>             the type of the exception
	 * @param type            the type of the exception
	 * @param message         an explanation of what went wrong
	 * @param cause           the cause of the exception ({@code null} if absent)
	 * @param apiInStacktrace true if API elements should show up in the stack trace
	 * @return the exception
	 * @throws AssertionError if {@code type} is null
	 */
	public <E extends RuntimeException> RuntimeException createException(Class<E> type, String message, Throwable cause,
	                                                                     boolean apiInStacktrace)
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
		// If apiInStacktrace is false, we need to strip out the top 3 stack trace elements. But we are
		// also forced to strip out the exception cause because it was thrown inside our API.
		assert (type != null) : "type may not be null";
		try
		{
			boolean withCause = apiInStacktrace && cause != null;
			MethodHandle constructor = getConstructor(type, withCause);
			// Convert the exception type to RuntimeException
			constructor = constructor.asType(constructor.type().changeReturnType(RuntimeException.class));

			RuntimeException result;
			if (withCause)
				result = (RuntimeException) constructor.invokeExact(message, cause);
			else
				result = (RuntimeException) constructor.invokeExact(message);
			if (!apiInStacktrace)
				removeLibraryFromStacktrace(result);
			return result;
		}
		catch (Throwable e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * @param type      the type of the exception
	 * @param withCause true if the constructor takes an exception cause
	 * @return the constructor of the exception
	 * @throws Throwable if an error occurs
	 */
	private MethodHandle getConstructor(Class<?> type, boolean withCause) throws Throwable
	{
		try
		{
			if (withCause)
				return classToConstructorsWithCause.computeIfAbsent(type, computeConstructorWithCause);
			return classToConstructorsWithoutCause.computeIfAbsent(type, computeConstructorWithoutCause);
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
	 * @param throwable the {@code Throwable} to process
	 */
	private void removeLibraryFromStacktrace(Throwable throwable)
	{
		filterStacktrace(throwable, element ->
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
	 * @param throwable     the {@code Throwable} to process
	 * @param elementFilter returns true if the current stack trace element and methods it invokes
	 *                      should be removed
	 */
	public void filterStacktrace(Throwable throwable, Predicate<StackTraceElement> elementFilter)
	{
		// Method needs to be public to hide 3rd-party verifiers located in in other packages
		StackTraceElement[] elements = throwable.getStackTrace();
		int i = elements.length - 1;
		while (true)
		{
			StackTraceElement element = elements[i];
			if (elementFilter.test(element))
				break;
			if (i == 0)
				return;
			--i;
		}
		throwable.setStackTrace(Arrays.copyOfRange(elements, i + 1, elements.length));
	}
}