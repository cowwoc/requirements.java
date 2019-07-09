/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class NumberTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "");
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
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new Requirements(scope).requireThat(actual, "actual").isBetweenClosed(first, last);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Requirements(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer actual = 0;
			requirements.requireThat(actual, "actual").isNotNegative();

			actual = 1;
			requirements.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Requirements(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Requirements(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer actual = -1;
			requirements.requireThat(actual, "actual").isNotZero();

			actual = 1;
			requirements.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Requirements(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer actual = 0;
			requirements.requireThat(actual, "actual").isNotPositive();

			actual = -1;
			requirements.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new Requirements(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isLessThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new Requirements(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new Requirements(scope).requireThat(actual, "actual").isLessThanOrEqualTo(2);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(0, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(2);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void byteIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte actual = (byte) 1;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void byteIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte actual = (byte) 1;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void shortIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short actual = (short) 1;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void shortIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short actual = (short) 1;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void intIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void intIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void longIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			long actual = 1L;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void longIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			long actual = 1L;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void floatIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			float actual = 1.0F;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void floatIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			float actual = 1.0F;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void doubleIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void doubleIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void intIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void intIsWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void intIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNotWholeNumber();
		}
	}

	@Test
	public void intIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 2;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(1);
		}
	}

	@Test
	public void intIsMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(actual);
		}
	}

	@Test
	public void intIsMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_ZeroTopAndBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(2);
		}
	}

	@Test
	public void intIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNotMultipleOf(2);
		}
	}

	@Test
	public void doubleIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test
	public void doubleIsWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 0.0;
			new Requirements(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsWholeNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isWholeNumber();
		}
	}

	@Test
	public void doubleIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isNotWholeNumber();
		}
	}

	@Test
	public void doubleIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 2.2;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(1.1);
		}
	}

	@Test
	public void doubleIsMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(actual);
		}
	}

	@Test
	public void doubleIsMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 0;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(1.1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(0.0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isMultipleOf(1.2);
		}
	}

	@Test
	public void doubleIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.1;
			new Requirements(scope).requireThat(actual, "actual").isNotMultipleOf(1.2);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Integer actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 5;
			new Requirements(scope).withAssertionsEnabled().requireThat(actual, "actual").isGreaterThan(10);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 5.5;
			new Requirements(scope).withAssertionsEnabled().requireThat(actual, "actual").isGreaterThan(10.5);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsMultipleOfNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			new Requirements(scope).validateThat(actual, "actual").isMultipleOf(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsMultipleOfWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			new Requirements(scope).validateThat(actual, "actual").isMultipleOf(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsNotMultipleOfNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			new Requirements(scope).validateThat(actual, "actual").isNotMultipleOf(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsNotMultipleOfWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			new Requirements(scope).validateThat(actual, "actual").isNotMultipleOf(null, "name");
		}
	}

	@Test
	public void validateThatNullIsNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNegative().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotNegative().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isZero().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotZero().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isPositive().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotPositive().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isWholeNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotWholeNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isMultipleOf(5.0).isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotMultipleOf(5.0).isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
