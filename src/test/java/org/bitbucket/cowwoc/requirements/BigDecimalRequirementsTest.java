/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.math.BigDecimal;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class BigDecimalRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "");
	}

	@Test
	@SuppressWarnings("deprecation")
	public void isInClosedRange_actualIsLowerBoundRange()
	{
		BigDecimal actual = BigDecimal.ZERO;
		Range<BigDecimal> range = Range.closed(BigDecimal.ZERO, BigDecimal.valueOf(2));
		Requirements.requireThat(actual, "actual").isIn(range);
	}

	@Test
	public void isIn_actualIsLowerBound()
	{
		BigDecimal actual = BigDecimal.ZERO;
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void isInClosedRange()
	{
		BigDecimal actual = BigDecimal.ONE;
		Range<BigDecimal> range = Range.closed(BigDecimal.ZERO, BigDecimal.valueOf(2));
		Requirements.requireThat(actual, "actual").isIn(range);
	}

	@Test
	public void isInRange()
	{
		BigDecimal actual = BigDecimal.ONE;
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void isInClosedRange_actualIsUpperBound()
	{
		BigDecimal actual = BigDecimal.valueOf(2);
		Range<BigDecimal> range = Range.closed(BigDecimal.ZERO, BigDecimal.valueOf(2));
		Requirements.requireThat(actual, "actual").isIn(range);
	}

	@Test
	public void isIn_actualIsLast()
	{
		BigDecimal actual = BigDecimal.valueOf(2);
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void isInOpenRange_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Range<BigDecimal> range = Range.open(BigDecimal.valueOf(10), BigDecimal.valueOf(20));
		Requirements.requireThat(actual, "actual").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void isInClosedRange_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Range<BigDecimal> range = Range.closed(BigDecimal.valueOf(10), BigDecimal.valueOf(20));
		Requirements.requireThat(actual, "actual").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		BigDecimal first = BigDecimal.valueOf(10);
		BigDecimal last = BigDecimal.valueOf(20);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		BigDecimal actual = BigDecimal.valueOf(-1);
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		BigDecimal actual = BigDecimal.ZERO;
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test
	public void isNotNegative()
	{
		Requirements.requireThat(BigDecimal.ZERO, "actual").isNotNegative();
		Requirements.requireThat(BigDecimal.ONE, "actual").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		BigDecimal actual = BigDecimal.valueOf(-1);
		Requirements.requireThat(actual, "actual").isNotNegative();
	}

	@Test
	public void isZero()
	{
		Requirements.requireThat(BigDecimal.ZERO, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		BigDecimal actual = BigDecimal.valueOf(-1);
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		BigDecimal actual = BigDecimal.valueOf(0.1);
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test
	public void isNotZero()
	{
		Requirements.requireThat(BigDecimal.valueOf(-1), "actual").isNotZero();
		Requirements.requireThat(BigDecimal.valueOf(0.1), "actual").isNotZero();
		Requirements.requireThat(BigDecimal.ONE, "actual").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		Requirements.requireThat(BigDecimal.ZERO, "actual").isNotZero();
	}

	@Test
	public void isPositive()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		BigDecimal actual = BigDecimal.ZERO;
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		BigDecimal actual = BigDecimal.valueOf(-1);
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test
	public void isNotPositive()
	{
		Requirements.requireThat(BigDecimal.ZERO, "actual").isNotPositive();
		Requirements.requireThat(BigDecimal.valueOf(-1), "actual").isNotPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isNotPositive();
	}

	@Test
	public void isLessThanVariable()
	{
		BigDecimal actual = BigDecimal.ZERO;
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
	}

	@Test
	public void isLessThanConstant()
	{
		BigDecimal actual = BigDecimal.ZERO;
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(2);
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(2);
		Requirements.requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(BigDecimal.ONE, "expected");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(3);
		Requirements.requireThat(actual, "actual").
			isLessThanOrEqualTo(BigDecimal.valueOf(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(3);
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(BigDecimal.valueOf(2));
	}

	@Test
	public void isGreaterThanVariable()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO, "expected");
	}

	@Test
	public void isGreaterThanConstant()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO, " ");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.valueOf(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThan(BigDecimal.valueOf(2));
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").
			isGreaterThanOrEqualTo(BigDecimal.ONE, "expected");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").
			isGreaterThanOrEqualTo(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").
			isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").
			isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.ONE;
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(BigDecimal.valueOf(2));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void precisionIsInClosedRange()
	{
		BigDecimal actual = BigDecimal.valueOf(1234, 2);
		Range<Integer> range = Range.closed(3, 5);
		Requirements.requireThat(actual, "actual").precision().isIn(range);
	}

	@Test
	public void precisionIsInRange()
	{
		BigDecimal actual = BigDecimal.valueOf(1234, 2);
		int first = 3;
		int last = 5;
		Requirements.requireThat(actual, "actual").precision().isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void precisionIsInClosedRange_actualIsBelow()
	{
		BigDecimal actual = BigDecimal.valueOf(123, 2);
		Range<Integer> range = Range.closed(10, 20);
		Requirements.requireThat(actual, "actual").precision().isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precisionIsInClosedRange_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(123, 2);
		int first = 10;
		int last = 20;
		Requirements.requireThat(actual, "actual").precision().isIn(first, last);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void scaleInClosedRange()
	{
		BigDecimal actual = BigDecimal.valueOf(1234, 4);
		Range<Integer> range = Range.closed(3, 5);
		Requirements.requireThat(actual, "actual").scale().isIn(range);
	}

	@Test
	public void scaleInRange()
	{
		BigDecimal actual = BigDecimal.valueOf(1234, 4);
		int first = 3;
		int last = 5;
		Requirements.requireThat(actual, "actual").scale().isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void scaleIsInClosedRange_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(123, 2);
		Range<Integer> range = Range.closed(10, 20);
		Requirements.requireThat(actual, "actual").scale().isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsInRange_actualIsAbove()
	{
		BigDecimal actual = BigDecimal.valueOf(123, 2);
		int first = 10;
		int last = 20;
		Requirements.requireThat(actual, "actual").scale().isIn(first, last);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		BigDecimal actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
