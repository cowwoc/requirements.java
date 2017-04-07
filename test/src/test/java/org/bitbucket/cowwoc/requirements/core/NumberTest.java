/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class NumberTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isInRange_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			int first = 0;
			int last = 2;
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isInRange_actualIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			int first = 0;
			int last = 2;
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isInRange_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			int first = 0;
			int last = 2;
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			int first = 10;
			int last = 20;
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			Integer actual = 0;
			verifiers.requireThat(actual, "actual").isNotNegative();

			actual = 1;
			verifiers.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Verifiers(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			Integer actual = -1;
			verifiers.requireThat(actual, "actual").isNotZero();

			actual = 1;
			verifiers.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = -1;
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			Integer actual = 0;
			verifiers.requireThat(actual, "actual").isNotPositive();

			actual = -1;
			verifiers.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 0;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 2;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isLessThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 3;
			new Verifiers(scope).requireThat(actual, "actual").isLessThanOrEqualTo(2);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(0, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(2);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(2);
		}
	}

	@Test
	public void isFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new Verifiers(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	public void isNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new Verifiers(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test
	public void isNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 0.0 / 0.0;
			new Verifiers(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void isNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 0.0 / 0.0;
			new Verifiers(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void byteIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte actual = (byte) 1;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void byteIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte actual = (byte) 1;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shortIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short actual = (short) 1;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void shortIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short actual = (short) 1;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void intIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void intIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 1;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void longIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			long actual = 1L;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void longIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			long actual = 1L;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			float actual = 1.0F;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void floatIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			float actual = 1.0F;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void doubleIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 1.0;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Integer actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int actual = 5;
			new Verifiers(scope).withAssertionsEnabled().requireThat(actual, "actual").isGreaterThan(10);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			double actual = 5.5;
			new Verifiers(scope).withAssertionsEnabled().requireThat(actual, "actual").isGreaterThan(10.5);
		}
	}
}
