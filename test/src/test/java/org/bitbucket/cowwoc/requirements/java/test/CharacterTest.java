/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class CharacterTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isBetween_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '0';
			char first = '0';
			char last = '2';
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetween_actualIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			char first = '0';
			char last = '2';
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '2';
			char first = '0';
			char last = '2';
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			char first = '3';
			char last = '4';
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetweenClosed_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '2';
			char first = '0';
			char last = '2';
			new Requirements(scope).requireThat(actual, "actual").isBetweenClosed(first, last);
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void byteIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void byteIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Character actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char actual = '1';
			new Requirements(scope).withAssertionsEnabled().requireThat(actual, "actual").
				isGreaterThan(actual);
		}
	}
}
