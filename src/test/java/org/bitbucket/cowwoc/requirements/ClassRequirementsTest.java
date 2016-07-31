/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.Assertions;
import org.bitbucket.cowwoc.requirements.Requirements;
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

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmptyForCollection()
	{
		Class<Object> parameter = Object.class;
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isSupertypeOfTrue()
	{
		Class<Object> parameter = Object.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Random.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOfFalse()
	{
		Class<Random> parameter = Random.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Object.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOfNullType()
	{
		Class<Random> parameter = Random.class;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOfNullParameter()
	{
		Class<Random> parameter = null;
		Requirements.requireThat(parameter, "parameter").isSupertypeOf(Random.class);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Class<?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
