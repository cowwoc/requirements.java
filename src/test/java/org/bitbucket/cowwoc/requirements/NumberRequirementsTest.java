/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class NumberRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		Integer actual = 0;
		int first = 0;
		int last = 2;
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isInRange_expectedIsInBounds()
	{
		Integer actual = 1;
		int first = 0;
		int last = 2;
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		Integer actual = 2;
		int first = 0;
		int last = 2;
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBelow()
	{
		Integer actual = 1;
		int first = 10;
		int last = 20;
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isNegative_expectedIsNegativeOne()
	{
		Integer actual = -1;
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_expectedIsZero()
	{
		Integer actual = 0;
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_expectedIsOne()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isNegative();
	}

	@Test
	public void isNotNegative()
	{
		Requirements.requireThat(0, "actual").isNotNegative();
		Requirements.requireThat(1, "actual").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_expectedIsNegativeOne()
	{
		Integer actual = -1;
		Requirements.requireThat(actual, "actual").isNotNegative();
	}

	@Test
	public void isZero()
	{
		Requirements.requireThat(0, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_expectedIsOne()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_expectedIsNegativeOne()
	{
		Integer actual = -1;
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_expectedIsOne()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isZero();
	}

	@Test
	public void isNotZero()
	{
		Requirements.requireThat(-1, "actual").isNotZero();
		Requirements.requireThat(1, "actual").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		Integer actual = 0;
		Requirements.requireThat(actual, "actual").isNotZero();
	}

	@Test
	public void isPositive()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_expectedIsZero()
	{
		Integer actual = 0;
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_expectedIsNegativeOne()
	{
		Integer actual = -1;
		Requirements.requireThat(actual, "actual").isPositive();
	}

	@Test
	public void isNotPositive()
	{
		Requirements.requireThat(0, "actual").isNotPositive();
		Requirements.requireThat(-1, "actual").isNotPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_expectedIsOne()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isNotPositive();
	}

	@Test
	public void isLessThanVariable()
	{
		Integer actual = 0;
		Requirements.requireThat(actual, "actual").isLessThan(1, "expected");
	}

	@Test
	public void isLessThanConstant()
	{
		Integer actual = 0;
		Requirements.requireThat(actual, "actual").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isLessThan(1, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsGreater()
	{
		Integer actual = 2;
		Requirements.requireThat(actual, "actual").isLessThan(1, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsGreater()
	{
		Integer actual = 2;
		Requirements.requireThat(actual, "actual").isLessThan(1);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(1, "expected");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsGreater()
	{
		Integer actual = 3;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(2, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsGreater()
	{
		Integer actual = 3;
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(2);
	}

	@Test
	public void isGreaterThanVariable()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(0, "expected");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(1, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsLess()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(2, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsLess()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThan(2);
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(1, "expected");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsLess()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(2, "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsLess()
	{
		Integer actual = 1;
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(2);
	}

	@Test
	public void isFinite()
	{
		Double actual = 1.0;
		Requirements.requireThat(actual, "actual").isFinite();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFinite_False()
	{
		Double actual = 1.0 / 0.0;
		Requirements.requireThat(actual, "actual").isFinite();
	}

	@Test
	public void isNotFinite()
	{
		Double actual = 1.0 / 0.0;
		Requirements.requireThat(actual, "actual").isNotFinite();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotFinite_False()
	{
		Double actual = 1.0;
		Requirements.requireThat(actual, "actual").isNotFinite();
	}

	@Test
	public void isNumber()
	{
		Double actual = 1.0;
		Requirements.requireThat(actual, "actual").isNumber();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNumber_False()
	{
		Double actual = 0.0 / 0.0;
		Requirements.requireThat(actual, "actual").isNumber();
	}

	@Test
	public void isNotNumber()
	{
		Double actual = 0.0 / 0.0;
		Requirements.requireThat(actual, "actual").isNotNumber();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNumber_False()
	{
		Double actual = 1.0;
		Requirements.requireThat(actual, "actual").isNotNumber();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Integer actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledInteger()
	{
		int actual = 5;
		new AssertionVerifier(true).requireThat(actual, "actual").isGreaterThan(10);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledDouble()
	{
		double actual = 5.5;
		new AssertionVerifier(true).requireThat(actual, "actual").isGreaterThan(10.5);
	}
}
