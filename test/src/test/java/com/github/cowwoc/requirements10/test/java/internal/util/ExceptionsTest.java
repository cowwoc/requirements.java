/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java.internal.util;

import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.ValidationFailures;
import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Exceptions;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements10.java.internal.util.Exceptions.LIBRARY_PACKAGE;
import static com.github.cowwoc.requirements10.java.internal.util.Exceptions.TEST_PACKAGE;

public final class ExceptionsTest
{
	@Test
	public void throwWithoutCleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(false).
					recordStacktrace(false);
			}

			try
			{
				ValidationFailures failures = validators.checkIf((Object) null, "nullActual").isNotNull().
					elseGetFailures();
				failures.addAll(validators.checkIf(5, "fiveActual").isLessThan(3).elseGetFailures());
				failures.throwOnFailure();
			}
			catch (MultipleFailuresException e)
			{
				validators.requireThat(isStackTraceClean(e), "isStackTraceClean").isEqualTo(false);
			}
		}
	}

	@Test
	public void throwWithCleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(true).
					recordStacktrace(true);
			}

			try
			{
				ValidationFailures failures = validators.checkIf((Object) null, "nullActual").isNotNull().
					elseGetFailures();
				failures.addAll(validators.checkIf(5, "fiveActual").isLessThan(3).elseGetFailures());
				failures.throwOnFailure();
			}
			catch (MultipleFailuresException e)
			{
				validators.requireThat(isStackTraceClean(e), "isStackTraceClean").isEqualTo(true);

				// Ensure that stack trace is not missing any elements above the current method.
				// Throwables are not comparable, but their stack traces are.
				StackTraceElement[] expected = new AssertionError("\"actual\" may not be null").
					getStackTrace();
				for (ValidationFailure failure : e.getFailures())
				{
					StackTraceElement[] actual = failure.getException().getStackTrace();

					// The actual stack trace might contain more elements than the expected stack trace. Truncate it
					// to the same length.
					actual = Arrays.copyOfRange(actual, actual.length - expected.length, actual.length);

					// The stack traces should be identical, except for the line number of the top-most element
					copyTopLineNumber(expected, actual);
					validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
				}
			}
		}
	}

	/**
	 * @param throwable a throwable
	 * @return true if the stack trace does not contain any references to our library
	 */
	private boolean isStackTraceClean(Throwable throwable)
	{
		for (StackTraceElement element : throwable.getStackTrace())
		{
			String className = element.getClassName();
			if (className.startsWith(LIBRARY_PACKAGE) && !className.startsWith(TEST_PACKAGE))
				return false;
		}
		Throwable cause = throwable.getCause();
		if (cause != null && !isStackTraceClean(cause))
			return false;
		for (Throwable suppressed : throwable.getSuppressed())
			if (!isStackTraceClean(suppressed))
				return false;
		return true;
	}

	/**
	 * Copies the line number from the top element of {@code first} to the top element of {@code second}.
	 *
	 * @param first  an exception stack trace
	 * @param second another exception stack trace
	 */
	private void copyTopLineNumber(StackTraceElement[] first, StackTraceElement[] second)
	{
		StackTraceElement firstElement = first[0];
		StackTraceElement secondElement = second[0];

		second[0] = new StackTraceElement(secondElement.getClassLoaderName(),
			secondElement.getModuleName(), secondElement.getModuleVersion(),
			secondElement.getClassName(), secondElement.getMethodName(), secondElement.getFileName(),
			firstElement.getLineNumber());
	}

	@Test
	public void recordStacktrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(false).
					recordStacktrace(true);
			}

			try
			{
				ValidationFailures failures = validators.checkIf((Object) null, "nullActual").isNotNull().
					elseGetFailures();
				failures.addAll(validators.checkIf(5, "fiveActual").isLessThan(3).elseGetFailures());
				failures.throwOnFailure();
			}
			catch (MultipleFailuresException e)
			{
				boolean isNotNullFound = false;
				for (ValidationFailure failure : e.getFailures())
				{
					Throwable exception = failure.getException();
					if (Exceptions.stacktraceContains(exception, "isNotNull"))
					{
						isNotNullFound = true;
						break;
					}
				}
				boolean isLessThanFound = false;
				for (ValidationFailure failure : e.getFailures())
				{
					Throwable exception = failure.getException();
					if (Exceptions.stacktraceContains(exception, "isNotNull"))
					{
						isLessThanFound = true;
						break;
					}
				}
				ValidationFailures failures = validators.checkIf(isNotNullFound, "isNotNullFound").isTrue().
					elseGetFailures();
				failures.addAll(validators.checkIf(isLessThanFound, "isLessThan").isTrue().elseGetFailures());
				failures.throwOnFailure();
			}
		}
	}

	@Test
	public void withoutRecordStacktraceAndCleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(false).
					recordStacktrace(false);
			}

			try
			{
				ValidationFailures failures = validators.checkIf((Object) null, "actual").isNotNull().
					elseGetFailures();
				failures.addAll(validators.checkIf(5, "actual").isLessThan(3).elseGetFailures());
				failures.throwOnFailure();
			}
			catch (MultipleFailuresException e)
			{
				// Ensure that stack trace is not missing any elements above the current method.
				// Throwables are not comparable, but their stack traces are.
				StackTraceElement[] expected = new AssertionError("\"actual\" may not be null").
					getStackTrace();
				for (ValidationFailure failure : e.getFailures())
				{
					StackTraceElement[] actual = failure.getException().getStackTrace();

					// The actual stack trace might contain more elements than the expected stack trace. Truncate it
					// to the same length.
					actual = Arrays.copyOfRange(actual, actual.length - expected.length, actual.length);

					// The stack traces should be identical, except for the line number of the top-most element
					copyTopLineNumber(expected, actual);
					validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
				}
			}
		}
	}

	@Test
	public void cleanStackTraceWithoutRecordStacktrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(true).
					recordStacktrace(false);
			}

			try
			{
				ValidationFailures failures = validators.checkIf((Object) null, "actual").isNotNull().
					elseGetFailures();
				failures.addAll(validators.checkIf(5, "actual").isLessThan(3).elseGetFailures());
				failures.throwOnFailure();
			}
			catch (MultipleFailuresException e)
			{
				// Ensure that stack trace is not missing any elements above the current method.
				// Throwables are not comparable, but their stack traces are.
				StackTraceElement[] expected = new AssertionError("\"actual\" may not be null").
					getStackTrace();
				for (ValidationFailure failure : e.getFailures())
				{
					StackTraceElement[] actual = failure.getException().getStackTrace();

					// The actual stack trace might contain more elements than the expected stack trace. Truncate it
					// to the same length.
					actual = Arrays.copyOfRange(actual, actual.length - expected.length, actual.length);

					// The stack traces should be identical, except for the line number of the top-most element
					copyTopLineNumber(expected, actual);
					validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
				}
			}
		}
	}

	@Test
	public void getMessagesWithoutRecordStacktrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.recordStacktrace(false);
			}

			ValidationFailures failures = validators.checkIf((Object) null, "actual").isNotNull().elseGetFailures();
			failures.addAll(validators.checkIf(5, "actual").isLessThan(3).elseGetFailures());
			validators.requireThat(failures.getMessages(), "messages").size().isEqualTo(2);
		}
	}
}