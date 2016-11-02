/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Random;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class ClassRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Class<Object> parameter = Object.class;
			new RequirementVerifier(scope).requireThat(parameter, null);
		}
	}

	@Test
	public void isSupertypeOf()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Class<Object> parameter = Object.class;
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isSupertypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Class<Random> parameter = Random.class;
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isSupertypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Class<Random> parameter = Random.class;
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isSupertypeOf(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_actualIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Class<Random> parameter = null;
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isSupertypeOf(Random.class);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Class<?> parameter = null;
			new AssertionVerifier(scope, false).requireThat(parameter, "parameter").isNotNull();
		}
	}
}
