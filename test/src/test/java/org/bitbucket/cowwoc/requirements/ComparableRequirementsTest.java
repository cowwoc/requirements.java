/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.time.Year;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class ComparableRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "");
		}
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year actual = Year.of(0);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isInRange()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year actual = Year.of(1);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year actual = Year.of(2);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year actual = Year.of(1);
			Year first = Year.of(10);
			Year last = Year.of(20);
			new RequirementVerifier(scope).requireThat(actual, "actual").isIn(first, last);
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").isLessThan(after, "after");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").isLessThan(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isLessThan(parameter, "parameter");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isLessThan(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(after, "after").isLessThan(before, "before");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(after, "after").isLessThan(before);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isLessThanOrEqualTo(parameter, "parameter");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isLessThanOrEqualTo(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(after, "after").
				isLessThanOrEqualTo(before, "before");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsAfter()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(1);
			Year after = Year.of(0);
			new RequirementVerifier(scope).requireThat(before, "before").isLessThanOrEqualTo(after);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(after, "after").isGreaterThan(before, "before");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(after, "after").isGreaterThan(before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isGreaterThan(parameter, "parameter");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "paramter").isGreaterThan(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").isGreaterThan(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").isGreaterThan(after);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isGreaterThanOrEqualTo(parameter, "parameter");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year parameter = Year.of(0);
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isGreaterThanOrEqualTo(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").
				isGreaterThanOrEqualTo(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsBefore()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new RequirementVerifier(scope).requireThat(before, "before").isGreaterThanOrEqualTo(after);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Year parameter = null;
			new AssertionVerifier(scope, false).requireThat(parameter, "parameter").isNotNull();
		}
	}
}
