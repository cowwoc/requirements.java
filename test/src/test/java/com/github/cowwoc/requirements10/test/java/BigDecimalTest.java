/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class BigDecimalTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isBetween_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsLast()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.valueOf(10);
			BigDecimal last = BigDecimal.valueOf(20);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetweenClosed_actualIsLast()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, true, last, true);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(BigDecimal.ZERO, "actual").isNotNegative();
			validators.requireThat(BigDecimal.ONE, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(0.1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(BigDecimal.valueOf(-1), "actual").isNotZero();
			validators.requireThat(BigDecimal.valueOf(0.1), "actual").isNotZero();
			validators.requireThat(BigDecimal.ONE, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "actual").isNotPositive();
			new TestValidatorsImpl(scope).requireThat(BigDecimal.valueOf(-1), "actual").
				isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void precisionIsBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 2);
			int first = 3;
			int last = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").precision().isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precisionIsBetween_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").precision().isBetween(first, last);
		}
	}

	@Test
	public void scaleIsBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 4);
			int first = 3;
			int last = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsBetween_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isBetween(first, last);
		}
	}

	@Test
	public void scaleIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isZero();
		}
	}

	@Test
	public void scaleIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotZero();
		}
	}

	@Test
	public void scaleIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isPositive();
		}
	}

	@Test
	public void scaleIsNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotPositive();
		}
	}

	@Test
	public void scaleIsNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNegative();
		}
	}

	@Test
	public void scaleIsNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").scale().isNotNegative();
		}
	}

	@Test
	public void isWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test
	public void isWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isWholeNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test
	public void isNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotWholeNumber();
		}
	}

	@Test
	public void isMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test
	public void isMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(actual);
		}
	}

	@Test
	public void isMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroTopAndBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMultipleOf(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotMultipleOf(BigDecimal.valueOf(2));
		}
	}

	/**
	 * BUG: Implementation used to check that {@code longValue() > 0}; ignoring the decimal component.
	 */
	@Test
	public void decimalIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("0.1");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void multipleFailuresNullIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be zero");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isZero().isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be zero");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotZero().isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullPrecision()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"actual.precision() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				precision().isNotEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullScale()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"actual.scale() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				scale().isNotEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a whole number");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isWholeNumber().isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be a whole number");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotWholeNumber().isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a multiple of 10");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isMultipleOf(BigDecimal.TEN).isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsMultipleOfWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
				"actual" must be a multiple of "other".
				other: 10""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isMultipleOf(BigDecimal.TEN, "other").isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be a multiple of 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotMultipleOf(BigDecimal.ONE).isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotMultipleOfWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
				"actual" may not be a multiple of "other".
				other: 1""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotMultipleOf(BigDecimal.ONE, "other").isNotEqualTo(BigDecimal.ONE).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
