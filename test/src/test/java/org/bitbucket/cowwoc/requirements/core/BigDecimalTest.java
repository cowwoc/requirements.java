/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class BigDecimalTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isIn_actualIsLowerBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isInRange()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isIn_actualIsLast()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			BigDecimal first = BigDecimal.ZERO;
			BigDecimal last = BigDecimal.valueOf(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_actualIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			BigDecimal first = BigDecimal.valueOf(10);
			BigDecimal last = BigDecimal.valueOf(20);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isNegative_actualIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ZERO;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_actualIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			verifier.requireThat(BigDecimal.ZERO, "actual").isNotNegative();
			verifier.requireThat(BigDecimal.ONE, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_actualIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			new RequirementVerifier(scope).requireThat(BigDecimal.ZERO, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_actualIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsZeroPointOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(0.1);
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_actualIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			verifier.requireThat(BigDecimal.valueOf(-1), "actual").isNotZero();
			verifier.requireThat(BigDecimal.valueOf(0.1), "actual").isNotZero();
			verifier.requireThat(BigDecimal.ONE, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			new RequirementVerifier(scope).requireThat(BigDecimal.ZERO, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ZERO;
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_actualIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(-1);
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			new RequirementVerifier(scope).requireThat(BigDecimal.ZERO, "actual").isNotPositive();
			new RequirementVerifier(scope).requireThat(BigDecimal.valueOf(-1), "actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_actualIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ZERO;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE,
				"expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ZERO;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE,
				"expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(BigDecimal.ONE);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(3);
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ZERO);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedNameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ZERO, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.ONE, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThan(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedNameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.ONE, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.ONE;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(BigDecimal.valueOf(2));
		}
	}

	@Test
	public void precisionIsInRange()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 2);
			int first = 3;
			int last = 5;
			new RequirementVerifier(scope).requireThat(actual, "actual").precision().isIn(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void precisionIsInClosedRange_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new RequirementVerifier(scope).requireThat(actual, "actual").precision().isIn(first, last);
		}
	}

	@Test
	public void scaleInRange()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(1234, 4);
			int first = 3;
			int last = 5;
			new RequirementVerifier(scope).requireThat(actual, "actual").scale().isIn(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void scaleIsInRange_actualIsAbove()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			BigDecimal actual = BigDecimal.valueOf(123, 2);
			int first = 10;
			int last = 20;
			new RequirementVerifier(scope).requireThat(actual, "actual").scale().isIn(first, last);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			BigDecimal actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
