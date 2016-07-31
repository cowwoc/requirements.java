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
public final class RequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isEqualsTrue()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isEqualTo(parameter);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualsFalse()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isEqualTo(new Object());
	}

	@Test
	public void isNotEqualsTrue()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isNotEqualTo(new Object());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEqualsFalse()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isNotEqualTo(parameter);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOfNullParameter()
	{
		Random parameter = null;
		Requirements.requireThat(parameter, "parameter").isInstanceOf(Random.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOfNullValue()
	{
		Random parameter = new Random();
		Requirements.requireThat(parameter, "parameter").isInstanceOf(null);
	}

	@Test
	public void isInstanceOfTrue()
	{
		Random parameter = new Random();
		Requirements.requireThat(parameter, "parameter").isInstanceOf(Random.class).
			isInstanceOf(Object.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOfFalse()
	{
		Random parameter = new Random();
		Requirements.requireThat(parameter, "parameter").isInstanceOf(String.class);
	}

	@Test
	public void isNullTrue()
	{
		Object parameter = null;
		Requirements.requireThat(parameter, "parameter").isNull();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNullFalse()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isNull();
	}

	@Test
	public void isNotNullTrue()
	{
		Object parameter = new Object();
		Requirements.requireThat(parameter, "parameter").isNotNull();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNullFalse()
	{
		Object parameter = null;
		Requirements.requireThat(parameter, "parameter").isNotNull();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNullCustomException()
	{
		Object parameter = null;
		Requirements.requireThat(parameter, "parameter").withException(IllegalStateException.class).
			isNotNull();
	}

	/**
	 * BUG: {@code Requirements.withException(Class<? extends RuntimeException>)} was throwing a
	 * {@code ClassCastException} if the instance was anything other than
	 * {@code ObjectRequirementsImpl}.
	 */
	@Test
	public void customExceptionRequirementSubclass()
	{
		int parameter = 5;
		Requirements.requireThat(parameter, "parameter").withException(IllegalStateException.class).
			isNotNull();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Object parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}