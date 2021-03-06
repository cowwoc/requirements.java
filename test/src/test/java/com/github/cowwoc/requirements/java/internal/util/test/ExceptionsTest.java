/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class ExceptionsTest
{
	private final Exceptions exceptions = new Exceptions();

	/**
	 * Regression test. Exceptions.createException() was throwing:
	 * <p>
	 * {@code java.lang.invoke.WrongMethodTypeException: expected (String,Throwable)RuntimeException but
	 * found (String)RuntimeException}
	 */
	@Test
	@SuppressWarnings("ThrowableNotThrown")
	public void createExceptionWithCauseButNotInApi()
	{
		exceptions.createException(RuntimeException.class, "message", new Exception(), false);
	}

	/**
	 * Ensures that the library optimizes {@code NullPointerException}.
	 */
	@Test
	public void nullPointerExceptionIsOptimized()
	{
		RuntimeException result = exceptions.createException(NullPointerException.class, "message", null, true);
		boolean optimizedException = exceptions.isOptimizedException(result.getClass());
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).putContext("exception", exceptions.getClass()).
				requireThat(optimizedException, "optimizedException").isTrue();
		}
	}

	/**
	 * Ensures that the library optimizes {@code IllegalArgumentException}.
	 */
	@Test
	public void illegalArgumentExceptionIsOptimized()
	{
		RuntimeException result = exceptions.createException(IllegalArgumentException.class, "message", null, true);
		boolean optimizedException = exceptions.isOptimizedException(result.getClass());
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).putContext("exception", exceptions.getClass()).
				requireThat(optimizedException, "optimizedException").isTrue();
		}
	}

	/**
	 * Ensures that the library does not optimize {@code IllegalStateException}.
	 */
	@Test
	public void illegalStateExceptionIsOptimized()
	{
		RuntimeException result = exceptions.createException(IllegalStateException.class, "message", null, true);
		boolean optimizedException = exceptions.isOptimizedException(result.getClass());
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).putContext("exception", exceptions.getClass()).
				requireThat(optimizedException, "optimizedException").isFalse();
		}
	}

	@Test
	public void optimizedExceptionRemovesLibraryFromStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			scope.getGlobalConfiguration().withCleanStackTrace();
			new Requirements(scope).requireThat("value", "expected").isEqualTo("actual");
		}
		catch (IllegalArgumentException e)
		{
			for (StackTraceElement element : e.getStackTrace())
			{
				requireThat(element.getClassName(), "stacktrace").
					doesNotStartWith(Requirements.class.getPackage().getName());
			}
		}
	}
}
