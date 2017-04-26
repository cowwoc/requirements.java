/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class BooleanTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsTrue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Verifiers(scope).requireThat(actual, "actual").isTrue();
		}
	}

	@Test
	public void booleanIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Verifiers(scope).requireThat(actual, "actual").isFalse();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsFalse_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Verifiers(scope).requireThat(actual, "actual").isFalse();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void booleanIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void booleanIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(true);
			assert (false): "Expected verifier to throw an exception";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("Diff")):
				"Wasn't expecting boolean equals() to return diff.\n" +
				"\nActual:\n" + actualMessage;
		}
	}
}
