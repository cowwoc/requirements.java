/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

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
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isInLowerBound()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isInBounds()
	{
		BigDecimal parameter = BigDecimal.ONE;
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isInUpperBound()
	{
		BigDecimal parameter = BigDecimal.valueOf(2);
		BigDecimal first = BigDecimal.ZERO;
		BigDecimal last = BigDecimal.valueOf(2);
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseClosedRange()
	{
		BigDecimal parameter = BigDecimal.ONE;
		BigDecimal first = BigDecimal.valueOf(10);
		BigDecimal last = BigDecimal.valueOf(20);
		Requirements.requireThat(parameter, "parameter").isIn(first, last);
	}

	@Test
	public void isNegative_NegativeOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(-1);
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_Zero()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_One()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isNegative();
	}

	@Test
	public void isNotNegative()
	{
		Requirements.requireThat(BigDecimal.ZERO, "parameter").isNotNegative();
		Requirements.requireThat(BigDecimal.ONE, "parameter").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_NegativeOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(-1);
		Requirements.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test
	public void isZero()
	{
		Requirements.requireThat(BigDecimal.ZERO, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_One()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_NegativeOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(-1);
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_ZeroPointOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(0.1);
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_One()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isZero();
	}

	@Test
	public void isNotZero()
	{
		Requirements.requireThat(BigDecimal.valueOf(-1), "parameter").isNotZero();
		Requirements.requireThat(BigDecimal.valueOf(0.1), "parameter").isNotZero();
		Requirements.requireThat(BigDecimal.ONE, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_Integer()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		Requirements.requireThat(parameter, "parameter").isNotZero();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_BigDecimal()
	{
		Requirements.requireThat(BigDecimal.ZERO, "parameter").isNotZero();
	}

	@Test
	public void isPositive()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_Zero()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_NegativeOne()
	{
		BigDecimal parameter = BigDecimal.valueOf(-1);
		Requirements.requireThat(parameter, "parameter").isPositive();
	}

	@Test
	public void isNotPositive()
	{
		Requirements.requireThat(BigDecimal.ZERO, "parameter").isNotPositive();
		Requirements.requireThat(BigDecimal.valueOf(-1), "parameter").isNotPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_One()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isNotPositive();
	}

	@Test
	public void isLessThanVariable()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE, "value");
	}

	@Test
	public void isLessThanConstant()
	{
		BigDecimal parameter = BigDecimal.ZERO;
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Equal()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Equal()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Greater()
	{
		BigDecimal parameter = BigDecimal.valueOf(2);
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Greater()
	{
		BigDecimal parameter = BigDecimal.valueOf(2);
		Requirements.requireThat(parameter, "parameter").isLessThan(BigDecimal.ONE);
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(BigDecimal.ONE, "value");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_Greater()
	{
		BigDecimal parameter = BigDecimal.valueOf(3);
		Requirements.requireThat(parameter, "parameter").
			isLessThanOrEqualTo(BigDecimal.valueOf(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_Greater()
	{
		BigDecimal parameter = BigDecimal.valueOf(3);
		Requirements.requireThat(parameter, "parameter").isLessThanOrEqualTo(BigDecimal.valueOf(2));
	}

	@Test
	public void isGreaterThanVariable()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.ZERO, "value");
	}

	@Test
	public void isGreaterThanConstant()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.ZERO);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariableEmptyName()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.ZERO, " ");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Equal()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.ONE, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Equal()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Less()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.valueOf(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Less()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThan(BigDecimal.valueOf(2));
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").
			isGreaterThanOrEqualTo(BigDecimal.ONE, "value");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").
			isGreaterThanOrEqualTo(BigDecimal.ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariableEmptyName()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").
			isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_Less()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").
			isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_Less()
	{
		BigDecimal parameter = BigDecimal.ONE;
		Requirements.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(BigDecimal.valueOf(2));
	}

	@Test
	public void precisionIsInBounds()
	{
		BigDecimal parameter = BigDecimal.valueOf(1234, 2);
		int first = 3;
		int last = 5;
		Requirements.requireThat(parameter, "parameter").precision().isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precisionIsInFalseClosedRange()
	{
		BigDecimal parameter = BigDecimal.valueOf(123, 2);
		int first = 10;
		int last = 20;
		Requirements.requireThat(parameter, "parameter").precision().isIn(first, last);
	}

	@Test
	public void scaleInBounds()
	{
		BigDecimal parameter = BigDecimal.valueOf(1234, 4);
		int first = 3;
		int last = 5;
		Requirements.requireThat(parameter, "parameter").scale().isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsInFalseClosedRange()
	{
		BigDecimal parameter = BigDecimal.valueOf(123, 2);
		int first = 10;
		int last = 20;
		Requirements.requireThat(parameter, "parameter").scale().isIn(first, last);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		BigDecimal parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}
}
