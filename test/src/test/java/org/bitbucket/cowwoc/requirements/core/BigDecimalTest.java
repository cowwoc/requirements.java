/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
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
			new Verifiers(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isIn_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isInRange()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isIn_actualIsLast()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.valueOf(10);
			BigDecimal last = BigDecimal.valueOf(20);
			new Verifiers(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			verifiers.requireThat(BigDecimal.ZERO, "actual").isNotNegative();
			verifiers.requireThat(BigDecimal.ONE, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat(BigDecimal.ZERO, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(0.1);
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			verifiers.requireThat(BigDecimal.valueOf(-1), "actual").isNotZero();
			verifiers.requireThat(BigDecimal.valueOf(0.1), "actual").isNotZero();
			verifiers.requireThat(BigDecimal.ONE, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat(BigDecimal.ZERO, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new Verifiers(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat(BigDecimal.ZERO, "actual").isNotPositive();
			new Verifiers(scope).requireThat(BigDecimal.valueOf(-1), "actual").
				isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE,
				"expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE,
				"expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new Verifiers(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new Verifiers(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ONE;
			new Verifiers(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void precisionIsInRange()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 2);
			int first = 3;
			int last = 5;
			new Verifiers(scope).requireThat(actual, "actual").precision().isBetween(first,
				last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precisionIsInClosedRange_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new Verifiers(scope).requireThat(actual, "actual").precision().isBetween(first,
				last);
		}
	}

	@Test
	public void scaleInRange()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 4);
			int first = 3;
			int last = 5;
			new Verifiers(scope).requireThat(actual, "actual").scale().
				isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsInRange_actualIsAbove()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new Verifiers(scope).requireThat(actual, "actual").scale().
				isBetween(first, last);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			BigDecimal actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
