/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, true, last, true);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(BigDecimal.ZERO, "Actual").isNotNegative();
			validators.requireThat(BigDecimal.ONE, "Actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(0.1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(BigDecimal.valueOf(-1), "Actual").isNotZero();
			validators.requireThat(BigDecimal.valueOf(0.1), "Actual").isNotZero();
			validators.requireThat(BigDecimal.ONE, "Actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "Actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new TestValidatorsImpl(scope).requireThat(BigDecimal.ZERO, "Actual").isNotPositive();
			new TestValidatorsImpl(scope).requireThat(BigDecimal.valueOf(-1), "Actual").
				isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE, "Expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThanOrEqualTo(BigDecimal.ONE, "Expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isLessThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2), "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(BigDecimal.ZERO, "Expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThan(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(BigDecimal.ZERO, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(BigDecimal.ONE, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(BigDecimal.valueOf(2), "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThan(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThanOrEqualTo(BigDecimal.ONE, "Expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").precision().isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").precision().isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isBetween(first, last);
		}
	}

	@Test
	public void scaleIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isZero();
		}
	}

	@Test
	public void scaleIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotZero();
		}
	}

	@Test
	public void scaleIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isPositive();
		}
	}

	@Test
	public void scaleIsNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotPositive();
		}
	}

	@Test
	public void scaleIsNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNegative();
		}
	}

	@Test
	public void scaleIsNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").scale().isNotNegative();
		}
	}

	@Test
	public void isWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test
	public void isWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isWholeNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test
	public void isNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotWholeNumber();
		}
	}

	@Test
	public void isMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test
	public void isMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(actual);
		}
	}

	@Test
	public void isMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroTopAndBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotMultipleOf(BigDecimal.valueOf(2));
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test
	public void multipleFailuresNullIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isZero().isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotZero().isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullPrecision()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.precision() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				precision().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullScale()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "Actual.scale() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				scale().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isWholeNumber().isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotWholeNumber().isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isMultipleOf(BigDecimal.TEN).isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsMultipleOfWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isMultipleOf(BigDecimal.TEN, "name").isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotMultipleOf(BigDecimal.ONE).isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotMultipleOfWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to 1");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotMultipleOf(BigDecimal.ONE, "name").isNotEqualTo(BigDecimal.ONE).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
