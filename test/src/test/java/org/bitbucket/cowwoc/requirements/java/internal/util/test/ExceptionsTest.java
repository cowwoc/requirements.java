/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.util.Exceptions;
import org.testng.annotations.Test;

public final class ExceptionsTest
{
	private final Exceptions exceptions = new Exceptions();

	/**
	 * Regression test. Exceptions.createException() was throwing:
	 * <p>
	 * {@code java.lang.invoke.WrongMethodTypeException: expected (String,Throwable)RuntimeException but found (String)RuntimeException}
	 */
	@Test
	@SuppressWarnings("ThrowableResultIgnored")
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
		new Requirements().addContext("exception", exceptions.getClass()).
			requireThat(optimizedException, "optimizedException").isTrue();
	}

	/**
	 * Ensures that the library optimizes {@code IllegalArgumentException}.
	 */
	@Test
	public void illegalArgumentExceptionIsOptimized()
	{
		RuntimeException result = exceptions.createException(IllegalArgumentException.class, "message", null, true);
		boolean optimizedException = exceptions.isOptimizedException(result.getClass());
		new Requirements().addContext("exception", exceptions.getClass()).
			requireThat(optimizedException, "optimizedException").isTrue();
	}

	/**
	 * Ensures that the library does not optimize {@code IllegalStateException}.
	 */
	@Test
	public void illegalStateExceptionIsOptimized()
	{
		RuntimeException result = exceptions.createException(IllegalStateException.class, "message", null, true);
		boolean optimizedException = exceptions.isOptimizedException(result.getClass());
		new Requirements().addContext("exception", exceptions.getClass()).
			requireThat(optimizedException, "optimizedException").isFalse();
	}
}
