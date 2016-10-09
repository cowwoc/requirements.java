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
		Integer parameter = 1;
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isInLowerBound()
	{
		Integer parameter = 0;
		int first = 0;
		int last = 2;
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isInBounds()
	{
		Integer parameter = 1;
		int first = 0;
		int last = 2;
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isInUpperBound()
	{
		Integer parameter = 2;
		int first = 0;
		int last = 2;
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseClosedRange()
	{
		Integer parameter = 1;
		int first = 10;
		int last = 20;
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isNegative_NegativeOne()
	{
		Integer parameter = -1;
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_Zero()
	{
		Integer parameter = 0;
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_One()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test
	public void isNotNegative()
	{
		Requirements.requireThat(0, "parameter").isNotNegative();
		Requirements.requireThat(1, "parameter").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_NegativeOne()
	{
		Integer parameter = -1;
		Requirements.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test
	public void isZero()
	{
		Requirements.requireThat(0, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_One()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_NegativeOne()
	{
		Integer parameter = -1;
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_One()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test
	public void isNotZero()
	{
		Requirements.requireThat(-1, "parameter").isNotZero();
		Requirements.requireThat(1, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_Integer()
	{
		Integer parameter = 0;
		Requirements.requireThat(parameter, "parameter").isNotZero();
	}

	@Test
	public void isPositive()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_Zero()
	{
		Integer parameter = 0;
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_NegativeOne()
	{
		Integer parameter = -1;
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test
	public void isNotPositive()
	{
		Requirements.requireThat(0, "parameter").isNotPositive();
		Requirements.requireThat(-1, "parameter").isNotPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_One()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isNotPositive();
	}

	@Test
	public void isLessThanVariable()
	{
		Integer parameter = 0;
		Requirements.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test
	public void isLessThanConstant()
	{
		Integer parameter = 0;
		Requirements.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Equal()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Equal()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Greater()
	{
		Integer parameter = 2;
		Requirements.requireThat(parameter, "parameter").isLessThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Greater()
	{
		Integer parameter = 2;
		Requirements.requireThat(parameter, "parameter").isLessThan(1);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(1, "value");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_Greater()
	{
		Integer parameter = 3;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_Greater()
	{
		Integer parameter = 3;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(2);
	}

	@Test
	public void isGreaterThanVariable()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(0, "value");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Equal()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Equal()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Less()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Less()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(2);
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(1, "value");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_Less()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_Less()
	{
		Integer parameter = 1;
		Requirements.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(2);
	}

	@Test
	public void isFinite_True()
	{
		Double parameter = 1.0;
		Requirements.requireThat(parameter, "parameter").isFinite();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFinite_False()
	{
		Double parameter = 1.0 / 0.0;
		Requirements.requireThat(parameter, "parameter").isFinite();
	}

	@Test
	public void isNotFinite_True()
	{
		Double parameter = 1.0 / 0.0;
		Requirements.requireThat(parameter, "parameter").isNotFinite();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotFinite_False()
	{
		Double parameter = 1.0;
		Requirements.requireThat(parameter, "parameter").isNotFinite();
	}

	@Test
	public void isNumber_True()
	{
		Double parameter = 1.0;
		Requirements.requireThat(parameter, "parameter").isNumber();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNumber_False()
	{
		Double parameter = 0.0 / 0.0;
		Requirements.requireThat(parameter, "parameter").isNumber();
	}

	@Test
	public void isNotNumber_True()
	{
		Double parameter = 0.0 / 0.0;
		Requirements.requireThat(parameter, "parameter").isNotNumber();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNumber_False()
	{
		Double parameter = 1.0;
		Requirements.requireThat(parameter, "parameter").isNotNumber();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Integer parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledInteger()
	{
		int parameter = 5;
		new AssertionVerifier(true).requireThat(parameter, "parameter").isGreaterThan(10);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledDouble()
	{
		double parameter = 5.5;
		new AssertionVerifier(true).requireThat(parameter, "parameter").isGreaterThan(10.5);
	}
}
