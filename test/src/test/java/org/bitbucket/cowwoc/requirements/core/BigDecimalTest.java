/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class BigDecimalTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(null, actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("", actual);
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
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).isBetweenClosed(first, last);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat("actual", actual).isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			verifiers.requireThat("actual", BigDecimal.ZERO).isNotNegative();
			verifiers.requireThat("actual", BigDecimal.ONE).isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat("actual", actual).isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat("actual", BigDecimal.ZERO).isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat("actual", actual).isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(0.1);
			new Verifiers(scope).requireThat("actual", actual).isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			verifiers.requireThat("actual", BigDecimal.valueOf(-1)).isNotZero();
			verifiers.requireThat("actual", BigDecimal.valueOf(0.1)).isNotZero();
			verifiers.requireThat("actual", BigDecimal.ONE).isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat("actual", BigDecimal.ZERO).isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat("actual", actual).isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat("actual", BigDecimal.ZERO).isNotPositive();
			new Verifiers(scope).requireThat("actual", BigDecimal.valueOf(-1)).
				isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).
				isLessThan("expected", BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isLessThan("expected", BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThan("expected", BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat("actual", actual).isLessThan(BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo("expected", BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo("expected", BigDecimal.valueOf(2));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan("expected", BigDecimal.ZERO);
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan(" ", BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan("expected", BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isGreaterThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan("expected", BigDecimal.valueOf(2));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo("expected", BigDecimal.ONE);
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo(" ", BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo("expected", BigDecimal.valueOf(2));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).
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
			new Verifiers(scope).requireThat("actual", actual).precision().isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).precision().isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).scale().isBetween(first, last);
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
			new Verifiers(scope).requireThat("actual", actual).scale().isBetween(first, last);
		}
	}

	@Test
	public void scaleIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new Verifiers(scope).requireThat("actual", actual).scale().isZero();
		}
	}

	@Test
	public void scaleIsNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotZero();
		}
	}

	@Test
	public void scaleIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new Verifiers(scope).requireThat("actual", actual).scale().isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isPositive();
		}
	}

	@Test
	public void scaleIsNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotPositive_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 1);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotPositive();
		}
	}

	@Test
	public void scaleIsNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new Verifiers(scope).requireThat("actual", actual).scale().isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isNegative();
		}
	}

	@Test
	public void scaleIsNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 0);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsNotNegative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, -1);
			new Verifiers(scope).requireThat("actual", actual).scale().isNotNegative();
		}
	}

	@Test
	public void isWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isWholeNumber();
		}
	}

	@Test
	public void isWholeNumber_Zero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isWholeNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isWholeNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new Verifiers(scope).requireThat("actual", actual).isWholeNumber();
		}
	}

	@Test
	public void isNotWholeNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("1.1");
			new Verifiers(scope).requireThat("actual", actual).isNotWholeNumber();
		}
	}

	@Test
	public void isMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test
	public void isMultipleOf_Self()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(actual);
		}
	}

	@Test
	public void isMultipleOf_ZeroTop()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_ZeroTopAndBottom()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMultipleOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isMultipleOf(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isNotMultipleOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat("actual", actual).isNotMultipleOf(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			BigDecimal actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat("actual", actual).isNotNull();
		}
	}

	/**
	 * BUG: Implementation used to check that longValue() > 0; which fails for decimals.
	 */
	@Test
	public void decimalIsPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = new BigDecimal("0.1");
			new Verifiers(scope).requireThat("actual", actual).isPositive();
		}
	}
}
