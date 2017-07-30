/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.util;

import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ExceptionsTest
{
	/**
	 * Regression test. Exceptions.createException() was throwing:
	 * <p>
	 * {@code java.lang.invoke.WrongMethodTypeException: expected (String,Throwable)RuntimeException but found (String)RuntimeException}
	 */
	@Test
	@SuppressWarnings("ThrowableResultIgnored")
	public void createExceptionWithCauseButNotInApi()
	{
		new Exceptions().createException(RuntimeException.class, "message", new Exception(), false);
	}
}
