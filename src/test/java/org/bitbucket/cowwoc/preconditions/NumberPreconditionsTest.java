/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;
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
		Preconditions.requireThat(BigDecimal.ZERO, "parameter").isZero();
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
	public void isNotZero_ZeroPointOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(0.1);
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
		Preconditions.requireThat(BigDecimal.valueOf(0.1), "parameter").isNotZero();
		Preconditions.requireThat(1, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_Integer()
	{
		Integer parameter = 0;
		Preconditions.requireThat(parameter, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_BigDecimal()
	{
		Preconditions.requireThat(BigDecimal.ZERO, "parameter").isNotZero();
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
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(2, "value");
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
