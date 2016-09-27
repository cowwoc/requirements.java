/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.util;

import com.google.common.annotations.Beta;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.function.Predicate;
import org.bitbucket.cowwoc.requirements.Requirements;

/**
 * Exception helper functions.
 *
 * @author Gili Tzabari
 */
@Beta
public final class Exceptions
{
	private static final Lookup LOOKUP = MethodHandles.lookup();
	/**
	 * Name of boolean system property that, if set to true, prevents stack-traces from being trimmed.
	 */
	private static final String SHOW_FULL_STACKTRACE = Requirements.class.getPackage().getName() +
		".showFullStackTrace";

	/**
	 * Throws an exception with the specified message and cause.
	 * <p>
	 * @param <E>     the type of the exception
	 * @param type    the type of the exception
	 * @param message an explanation of what went wrong
	 * @param cause   the cause of the exception ({@code null} if absent)
	 * @return the exception
	 * @throws NullPointerException if {@code type} is null
	 */
	public static <E extends RuntimeException> RuntimeException createException(Class<E> type,
		String message, Throwable cause)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		try
		{
			MethodType constructorType;
			if (cause != null)
				constructorType = MethodType.methodType(void.class, String.class, Throwable.class);
			else
				constructorType = MethodType.methodType(void.class, String.class);
			MethodHandle constructor = LOOKUP.findConstructor(type, constructorType);
			// Convert from the actual exception type to RuntimeException
			constructor = constructor.asType(constructor.type().changeReturnType(RuntimeException.class));

			RuntimeException result;
			if (cause == null)
				result = (RuntimeException) constructor.invokeExact(message);
			else
				result = (RuntimeException) constructor.invokeExact(message, cause);
			if (!Boolean.getBoolean(SHOW_FULL_STACKTRACE))
				removeLibraryFromStacktrace(result);
			return result;
		}
		catch (Throwable e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * Removes references to this library from an exception stacktrace.
	 *
	 * @throws throwable the {@code Throwable} to process
	 */
	private static void removeLibraryFromStacktrace(Throwable throwable)
	{
		filterStacktrace(throwable, name ->
		{
			return name.startsWith(Requirements.class.getPackage().getName()) && !name.endsWith("Test");
		});
	}

	/**
	 * Runs a predicate against all lines of a stacktrace removing lines at or above those that match.
	 * <p>
	 * This method can be used to remove lines that hold little value for the end user (such as
	 * internal methods invoked by this library).
	 *
	 * @param throwable       the {@code Throwable} to process
	 * @param classNameFilter returns true if stack-trace elements should be removed at and above the
	 *                        current position
	 */
	public static void filterStacktrace(Throwable throwable, Predicate<String> classNameFilter)
	{
		StackTraceElement[] elements = throwable.getStackTrace();
		int i = elements.length - 1;
		while (true)
		{
			StackTraceElement element = elements[i];
			if (classNameFilter.test(element.getClassName()))
				break;
			if (i == 0)
				return;
			--i;
		}
		throwable.setStackTrace(Arrays.copyOfRange(elements, i + 1, elements.length));
	}

	/**
	 * Prevent construction.
	 */
	private Exceptions()
	{
	}
}
