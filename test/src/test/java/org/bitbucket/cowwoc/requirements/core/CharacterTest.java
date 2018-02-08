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
public final class CharacterTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Verifiers(scope).requireThat(null, actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Verifiers(scope).requireThat("", actual);
		}
	}

	@Test
	public void isInRange_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '0';
			char first = '0';
			char last = '2';
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test
	public void isInRange_actualIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			char first = '0';
			char last = '2';
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test
	public void isInRange_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '2';
			char first = '0';
			char last = '2';
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			char first = '3';
			char last = '4';
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void byteIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Verifiers(scope).requireThat("actual", actual).isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void byteIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Verifiers(scope).requireThat("actual", actual).isNotNull();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Character actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat("actual", actual).isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Verifiers(scope).withAssertionsEnabled().requireThat("actual", actual).
				isGreaterThan(actual);
		}
	}
}