/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class NumberPreconditionsTest
{
	@Test
	public void isNegative_NegativeOne()
	{
		Integer parameter = -1;
		Preconditions.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_Zero()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_One()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isNegative();
	}

	@Test
	public void isNotNegative()
	{
		Preconditions.requireThat(0, "parameter").isNotNegative();
		Preconditions.requireThat(1, "parameter").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_NegativeOne()
	{
		Integer parameter = -1;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test
	public void isPositive_One()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_Zero()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_NegativeOne()
	{
		Integer parameter = -1;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test
	public void isNotPositive()
	{
		Preconditions.requireThat(0, "parameter").isNotPositive();
		Preconditions.requireThat(-1, "parameter").isNotPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_One()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isNotPositive();
	}

	@Test
	public void isLessThan()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThan_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThan_Greater()
	{
		Integer parameter = 2;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test
	public void isLessThanOrEqualTo()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualTo_Greater()
	{
		Integer parameter = 3;
		Preconditions.requireThat(parameter, "parameter").isLessThan(2, "value");
	}

	@Test
	public void isGreaterThan()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(0, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThan_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThan_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2, "value");
	}

	@Test
	public void isGreaterThanOrEqualTo()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualTo_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(2, "value");
	}
}
