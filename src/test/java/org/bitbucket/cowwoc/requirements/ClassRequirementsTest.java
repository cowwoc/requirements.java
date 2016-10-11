/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Random;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class ClassRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Class<Object> parameter = Object.class;
		Requirements.requireThat(parameter, null);
	}

	@Test
	public void isSupertypeOf()
	{
		Class<Object> parameter = Object.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Random.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		Class<Random> parameter = Random.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Object.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedIsNull()
	{
		Class<Random> parameter = Random.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_actualIsNull()
	{
		Class<Random> parameter = null;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Random.class);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Class<?> parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}
}
