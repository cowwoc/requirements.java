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
public class NumberPreconditionsTest
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
	public void isInLowerBound()
	{
		Integer parameter = 0;
		Range<Integer> range = Range.closed(0, 2);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isInBounds()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(0, 2);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isInUpperBound()
	{
		Integer parameter = 2;
		Range<Integer> range = Range.closed(0, 2);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseOpenRange()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.open(10, 20);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseClosedRange()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(10, 20);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

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
	public void isZero()
	{
		Preconditions.requireThat(0, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_One()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_NegativeOne()
	{
		Integer parameter = -1;
		Preconditions.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_One()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isZero();
	}

	@Test
	public void isNotZero()
	{
		Preconditions.requireThat(-1, "parameter").isNotZero();
		Preconditions.requireThat(1, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_Integer()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isNotZero();
	}

	@Test
	public void isPositive()
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
	public void isLessThanVariable()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test
	public void isLessThanConstant()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Greater()
	{
		Integer parameter = 2;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Greater()
	{
		Integer parameter = 2;
		Preconditions.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(1, "value");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_Greater()
	{
		Integer parameter = 3;
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_Greater()
	{
		Integer parameter = 3;
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(2);
	}

	@Test
	public void isGreaterThanVariable()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(0, "value");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Equal()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2);
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(1, "value");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_Less()
	{
		Integer parameter = 1;
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(2);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Integer parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
