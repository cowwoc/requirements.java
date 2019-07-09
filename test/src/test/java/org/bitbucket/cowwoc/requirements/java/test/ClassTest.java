/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ClassTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Object> actual = Object.class;
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test
	public void isSupertypeOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Object> actual = Object.class;
			new Requirements(scope).requireThat(actual, "actual").isSupertypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = Random.class;
			new Requirements(scope).requireThat(actual, "actual").isSupertypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = Random.class;
			new Requirements(scope).requireThat(actual, "actual").isSupertypeOf(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Random> actual = null;
			new Requirements(scope).requireThat(actual, "actual").isSupertypeOf(Random.class);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Class<?> actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsSupertypeOfNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			new Requirements(scope).validateThat(actual, "actual").isSupertypeOf(null);
		}
	}

	@Test
	public void validateThatNullIsInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isSupertypeOf(Integer.class).isNotEqualTo(Double.class).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
