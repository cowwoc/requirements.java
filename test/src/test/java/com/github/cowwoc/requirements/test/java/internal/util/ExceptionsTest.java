/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.util;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class ExceptionsTest
{
	/**
	 * Regression test. Exceptions.createException() was throwing:
	 * <p>
	 * {@code java.lang.invoke.WrongMethodTypeException: expected (String,Throwable)RuntimeException but
	 * found (String)RuntimeException}
	 */
	@Test
	public void createExceptionWithCauseButNotInApi()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Exceptions exceptions = scope.getExceptions();
			exceptions.createException(RuntimeException.class, "message", new Exception(), false);
		}
	}

	/**
	 * Regression test: Exceptions.createException() was caching exceptions based on whether they had a cause,
	 * ignoring the value of cleanStackTrace. If it was invoked with cleanStackTrace = false, it would cache a
	 * non-optimized exception. If it was then invoked on the same exception with cleanStackTrace = true, it
	 * would return the cached value (non-optimized exception) even though an optimized exception existed.
	 */
	@Test
	public void optimizedExceptionLookupIgnoredValueOfCleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Exceptions exceptions = scope.getExceptions();

			Requirements requirements = new Requirements(scope);
			IllegalArgumentException unoptimizedException = exceptions.createException(
				IllegalArgumentException.class, "message", null, false);
			requirements.requireThat(exceptions.isOptimizedException(unoptimizedException.getClass()),
				"exceptions.isOptimizedException(unoptimizedException.getClass())").isFalse();

			IllegalArgumentException optimizedException = exceptions.createException(
				IllegalArgumentException.class, "message", null, true);
			requirements.requireThat(exceptions.isOptimizedException(optimizedException.getClass()),
				"exceptions.isOptimizedException(optimizedException.getClass())").isTrue();
		}
	}

	/**
	 * Ensures that the library optimizes {@code NullPointerException}.
	 */
	@Test
	public void nullPointerExceptionIsOptimized()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Needed to trigger the use of optimized exceptions
			scope.getGlobalConfiguration().withCleanStackTrace();

			Exceptions exceptions = scope.getExceptions();
			RuntimeException result = exceptions.createException(NullPointerException.class, "message", null, true);
			boolean optimizedException = exceptions.isOptimizedException(result.getClass());
			new Requirements(scope).withContext("exception", exceptions.getClass()).
				requireThat(optimizedException, "optimizedException").isTrue();
		}
	}

	/**
	 * Ensures that the library optimizes {@code IllegalArgumentException}.
	 */
	@Test
	public void illegalArgumentExceptionIsOptimized()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Needed to trigger the use of optimized exceptions
			scope.getGlobalConfiguration().withCleanStackTrace();

			Exceptions exceptions = scope.getExceptions();
			RuntimeException result = exceptions.createException(IllegalArgumentException.class, "message", null, true);
			boolean optimizedException = exceptions.isOptimizedException(result.getClass());
			new Requirements(scope).withContext("exception", exceptions.getClass()).
				requireThat(optimizedException, "optimizedException").isTrue();
		}
	}

	/**
	 * Ensures that the library does not optimize {@code IllegalStateException}.
	 */
	@Test
	public void illegalStateExceptionIsNotOptimized()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Exceptions exceptions = scope.getExceptions();

			Logger log = LoggerFactory.getLogger(ExceptionsTest.class);
			log.debug("*** The following exception is expected and does not denote a test failure ***");

			RuntimeException result = exceptions.createException(IllegalStateException.class, "message", null, true);
			boolean optimizedException = exceptions.isOptimizedException(result.getClass());
			new Requirements(scope).withContext("exception", result.getClass()).
				requireThat(optimizedException, "optimizedException").isFalse();
		}
	}

	@Test
	public void optimizedExceptionRemovesLibraryFromStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Needed to trigger the use of optimized exceptions
			scope.getGlobalConfiguration().withCleanStackTrace();

			Requirements requirements = new Requirements(scope);
			try
			{
				requirements.requireThat("value", "expected").isEqualTo("actual");
			}
			catch (IllegalArgumentException e)
			{
				Exceptions exceptions = scope.getExceptions();
				boolean optimizedException = exceptions.isOptimizedException(e.getClass());
				new Requirements(scope).withContext("exception", exceptions.getClass()).
					requireThat(optimizedException, "optimizedException").isTrue();

				for (StackTraceElement element : e.getStackTrace())
				{
					requirements.requireThat(element.getClassName(), "stacktrace").
						doesNotStartWith(Requirements.class.getPackage().getName());
				}
			}
		}
	}
}