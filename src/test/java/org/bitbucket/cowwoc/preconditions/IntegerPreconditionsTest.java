/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class IntegerPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isInTrue()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(0, 2);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalse()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(10, 20);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isNotNegativeTrue()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();

		parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegativeFalse()
	{
		Integer parameter = -1;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test
	public void isPositiveTrue()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositiveFalse()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test
	public void isGreaterThanIntegerTrue()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(0, "value");
	}

	@Test
	public void isGreaterThanLongTrue()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "intParameter").isGreaterThan(0L, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanIntegerFalse()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanLongFalseEquals()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1L, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanIntegerFalseSmaller()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanLongFalseSmaller()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2L, "value");
	}
}
