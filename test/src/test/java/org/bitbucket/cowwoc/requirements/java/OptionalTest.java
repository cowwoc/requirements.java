/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class OptionalTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isPresent()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresent_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").contains(5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").contains(6);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_actualIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, "actual").contains(5);
		}
	}

	@Test
	public void containsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new Requirements(scope).requireThat(actual, "actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmptyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new Requirements(scope).requireThat(actual, "actual").contains(null, "expected");
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Optional<?> actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
