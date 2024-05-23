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

import java.util.Collections;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class NumberTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isBetween_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			int first = 0;
			int last = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetween_actualIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			int first = 0;
			int last = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			int first = 0;
			int last = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			int first = 10;
			int last = 20;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetweenClosed_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			int first = 0;
			int last = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBetween(first, true, last, true);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer actual = 0;
			validators.requireThat(actual, "Actual").isNotNegative();

			actual = 1;
			validators.requireThat(actual, "Actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer actual = -1;
			validators.requireThat(actual, "Actual").isNotZero();

			actual = 1;
			validators.requireThat(actual, "Actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer actual = 0;
			validators.requireThat(actual, "Actual").isNotPositive();

			actual = -1;
			validators.requireThat(actual, "Actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1, "Expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThan(1);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isLessThanOrEqualTo(1, "Expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isLessThanOrEqualTo(2, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLessThanOrEqualTo(2);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(0, "Expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(1, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(2, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThan(2);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThanOrEqualTo(1, "Expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isGreaterThanOrEqualTo(2, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isGreaterThanOrEqualTo(2);
		}
	}

	@Test
	public void intIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(1);
		}
	}

	@Test
	public void intIsMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(actual);
		}
	}

	@Test
	public void intIsMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_ZeroTopAndBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(2);
		}
	}

	@Test
	public void intIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotMultipleOf(2);
		}
	}

	@Test
	public void doubleIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test
	public void doubleIsWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 0.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsWholeNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isWholeNumber();
		}
	}

	@Test
	public void doubleIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotWholeNumber();
		}
	}

	@Test
	public void doubleIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 2.2;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(1.1);
		}
	}

	@Test
	public void doubleIsMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(actual);
		}
	}

	@Test
	public void doubleIsMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(1.1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(0.0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMultipleOf(1.2);
		}
	}

	@Test
	public void doubleIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotMultipleOf(1.2);
		}
	}

	@Test
	public void multipleFailuresNullIsNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNegative().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotNegative().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isZero().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotZero().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isPositive().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotPositive().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isWholeNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotWholeNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isMultipleOf(5.0).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotMultipleOf(5.0).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			Integer expected = 5;
			List<String> expectedMessages = Collections.singletonList(
				"\"Actual\" must be equal to " + expected + ".\n" +

					"Actual: null");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isEqualTo(expected).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}