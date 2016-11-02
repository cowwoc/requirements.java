/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class NumberRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			int first = 0;
			int last = 2;
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isInRange_expectedIsInBounds()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			int first = 0;
			int last = 2;
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 2;
			int first = 0;
			int last = 2;
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			int first = 10;
			int last = 20;
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isNegative_expectedIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = -1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_expectedIsZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNegative_expectedIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void isNotNegative()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			verifier.requireThat(0, "actual").isNotNegative();
			verifier.requireThat(1, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegative_expectedIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = -1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void isZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			new RequirementVerifier(scope).requireThat(0, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_expectedIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isZero_expectedIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = -1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_expectedIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void isNotZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			verifier.requireThat(-1, "actual").isNotZero();
			verifier.requireThat(1, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotZero_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotZero();
		}
	}

	@Test
	public void isPositive()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_expectedIsZero()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositive_expectedIsNegativeOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = -1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void isNotPositive()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			verifier.requireThat(0, "actual").isNotPositive();
			verifier.requireThat(-1, "actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotPositive_expectedIsOne()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsGreater()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 2;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsGreater()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 2;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThan(1);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsGreater()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 3;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsGreater()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 3;
			new RequirementVerifier(scope).requireThat(actual, "actual").isLessThanOrEqualTo(2);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(0, "expected");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(0);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(1, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsLess()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsLess()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThan(2);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(1, "expected");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsLess()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(2, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsLess()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Integer actual = 1;
			new RequirementVerifier(scope).requireThat(actual, "actual").isGreaterThanOrEqualTo(2);
		}
	}

	@Test
	public void isFinite()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFinite_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0 / 0.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	public void isNotFinite()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0 / 0.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotFinite_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test
	public void isNumber()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNumber_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 0.0 / 0.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void isNotNumber()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 0.0 / 0.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNumber_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Double actual = 1.0;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Integer actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledInteger()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			int actual = 5;
			new AssertionVerifier(scope, true).requireThat(actual, "actual").isGreaterThan(10);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertionsEnabledDouble()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			double actual = 5.5;
			new AssertionVerifier(scope, true).requireThat(actual, "actual").isGreaterThan(10.5);
		}
	}
}
