/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Random;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class PreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isEqualsTrue()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "parameter").isEqualTo(parameter);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualsFalse()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "parameter").isEqualTo(new Object());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOfNullParameter()
	{
		Random parameter = null;
		Preconditions.requireThat(parameter, "parameter").isInstanceOf(Random.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOfNullValue()
	{
		Random parameter = new Random();
		Preconditions.requireThat(parameter, "parameter").isInstanceOf(null);
	}

	@Test
	public void isInstanceOfTrue()
	{
		Random parameter = new Random();
		Preconditions.requireThat(parameter, "parameter").isInstanceOf(Random.class).
			isInstanceOf(Object.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOfFalse()
	{
		Random parameter = new Random();
		Preconditions.requireThat(parameter, "parameter").isInstanceOf(String.class);
	}

	@Test
	public void isNotNullTrue()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "parameter").isNotNull();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNullFalse()
	{
		Object parameter = null;
		Preconditions.requireThat(parameter, "parameter").isNotNull();
	}

	@Test
	public void stateIsNotNullTrue()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "parameter").stateIsNotNull();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void stateIsNotNullFalse()
	{
		Object parameter = null;
		Preconditions.requireThat(parameter, "parameter").stateIsNotNull();
	}
}
