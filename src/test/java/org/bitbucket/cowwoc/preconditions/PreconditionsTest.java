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
	public void isNullTrue()
	{
		Object parameter = null;
		Preconditions.requireThat(parameter, "parameter").isNull();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNullFalse()
	{
		Object parameter = new Object();
		Preconditions.requireThat(parameter, "parameter").isNull();
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

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNullCustomException()
	{
		Object parameter = null;
		Preconditions.requireThat(parameter, "parameter").using(IllegalStateException.class).isNotNull();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Object parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
