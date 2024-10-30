/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.util;

import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailure;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception helper functions.
 */
public final class Exceptions
{
	/**
	 * The package name of this library.
	 */
	public static final String LIBRARY_PACKAGE = getParentPackage(Exceptions.class.getPackage().getName(), 3);
	/**
	 * The package name of the unit tests.
	 */
	public static final String TEST_PACKAGE = LIBRARY_PACKAGE + ".test";
	private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

	private Exceptions()
	{
	}

	/**
	 * @param name  the name of a Java package
	 * @param count the number of times to walk up the hierarchy
	 * @return the parent of the package
	 * @throws AssertionError if:
	 *                        <ul>
	 *                          <li>{@code name} is null</li>
	 *                          <li>{@code name} has no parent</li>
	 *                          <li>{@code count} is negative</li>
	 *                        </ul>
	 */
	private static String getParentPackage(String name, int count)
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
	 * Removes references to this library from an exception stack trace, so long as it does not result in any
	 * user code being removed.
	 *
	 * @param throwable the exception to process
	 * @throws NullPointerException if {@code throwable} is null
	 */
	public static void removeLibraryFromStackTrace(Throwable throwable)
	{
		// When we instantiate a new exception inside this method, we end up with:
		//
		// java.lang.IllegalArgumentException: message of top exception
		//   at com.github.cowwoc.requirements10.java.internal.util.Exceptions.createException(Exceptions.java:76)
		//   at com.github.cowwoc.requirements10.java.internal.util.ExceptionBuilder.build(ExceptionBuilder.java:179)
		//   at com.github.cowwoc.requirements10.java.internal.validator.SomeValidator.method(SomeValidator.java:1)
		//   at UserCode.method1(UserCode.java:1)
		// Caused by: message of underlying exception
		//   at Cause.method1(Cause.java:1)
		//
		// If cleanStackTrace is true, we strip any reference to the library so only user code remains.

		StackTraceElement[] stackTrace = throwable.getStackTrace();
		StackTraceElement[] newStackTrace = removeLibraryFromStackTrace(stackTrace);
		if (newStackTrace.length != stackTrace.length)
			throwable.setStackTrace(newStackTrace);

		Throwable cause = throwable.getCause();
		if (cause != null)
			removeLibraryFromStackTrace(cause);

		Throwable[] suppressedExceptions;
		if (throwable instanceof MultipleFailuresException mfe)
		{
			List<ValidationFailure> nestedFailures = mfe.getFailures();
			suppressedExceptions = new Throwable[nestedFailures.size()];
			for (int i = 0; i < suppressedExceptions.length; ++i)
				suppressedExceptions[i] = nestedFailures.get(i).getException();
		}
		else
			suppressedExceptions = throwable.getSuppressed();
		for (Throwable suppressed : suppressedExceptions)
			removeLibraryFromStackTrace(suppressed);
	}

	/**
	 * Removes references to this library from an exception stack trace, so long as it does not result in any
	 * user code being removed.
	 *
	 * @param elements the stack trace elements to process
	 * @return the updated stack trace elements
	 * @throws NullPointerException if {@code elements} are null
	 */
	private static StackTraceElement[] removeLibraryFromStackTrace(StackTraceElement[] elements)
	{
		List<StackTraceElement> linesToKeep = new ArrayList<>(elements.length);
		for (StackTraceElement element : elements)
		{
			String className = element.getClassName();
			if (className.startsWith(LIBRARY_PACKAGE) && !className.startsWith(TEST_PACKAGE))
				continue;
			linesToKeep.add(element);
		}
		if (linesToKeep.isEmpty())
			return EMPTY_STACK_TRACE;
		return linesToKeep.toArray(new StackTraceElement[0]);
	}

	/**
	 * @param exception an exception
	 * @param text      some text
	 * @return {@code true} if any of the exception's stack trace elements contain {@code text}
	 */
	public static boolean stacktraceContains(Throwable exception, String text)
	{
		return getStackTraceAsString(exception).contains(text);
	}

	/**
	 * Returns the stack trace of an exception
	 *
	 * @param throwable the exception
	 * @return the stack trace of the exception
	 */
	public static String getStackTraceAsString(Throwable throwable)
	{
		try (StringWriter stringWriter = new StringWriter())
		{
			try (PrintWriter pw = new PrintWriter(stringWriter))
			{
				throwable.printStackTrace(pw);
				return stringWriter.toString();
			}
		}
		catch (IOException e)
		{
			throw new AssertionError(e);
		}
	}
}