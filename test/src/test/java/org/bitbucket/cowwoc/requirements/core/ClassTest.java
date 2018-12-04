/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

import java.util.Random;

import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;

public final class ClassTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Object> actual = Object.class;
			new Verifiers(scope).requireThat(null, actual);
		}
	}

	@Test
	public void isSupertypeOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Object> actual = Object.class;
			new Verifiers(scope).requireThat("actual", actual).isSupertypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = Random.class;
			new Verifiers(scope).requireThat("actual", actual).isSupertypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = Random.class;
			new Verifiers(scope).requireThat("actual", actual).isSupertypeOf(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = null;
			new Verifiers(scope).requireThat("actual", actual).isSupertypeOf(Random.class);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Class<?> actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat("actual", actual).isNotNull();
		}
	}
}
