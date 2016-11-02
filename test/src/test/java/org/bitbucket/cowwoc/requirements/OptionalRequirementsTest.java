/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class OptionalRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.empty();
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.empty();
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isPresent()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.of(5);
			new RequirementVerifier(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresent_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.empty();
			new RequirementVerifier(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test
	public void isEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.empty();
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Optional<?> actual = Optional.of(5);
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Optional<?> actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
