/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.time.Year;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class YearRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		Year actual = Year.of(0);
		Year first = Year.of(0);
		Year last = Year.of(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isInRange()
	{
		Year actual = Year.of(1);
		Year first = Year.of(0);
		Year last = Year.of(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		Year actual = Year.of(2);
		Year first = Year.of(0);
		Year last = Year.of(2);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBefore()
	{
		Year actual = Year.of(1);
		Year first = Year.of(10);
		Year last = Year.of(20);
		Requirements.requireThat(actual, "actual").isIn(first, last);
	}

	@Test
	public void isLessThanVariable()
	{
		Year actual = Year.of(0);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1), "expected");
	}

	@Test
	public void isLessThanConstant()
	{
		Year actual = Year.of(0);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsAfter()
	{
		Year actual = Year.of(2);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsAfter()
	{
		Year actual = Year.of(2);
		Requirements.requireThat(actual, "actual").isLessThan(Year.of(1));
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(Year.of(1), "expected");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsAfter()
	{
		Year actual = Year.of(3);
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(Year.of(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsAfter()
	{
		Year actual = Year.of(3);
		Requirements.requireThat(actual, "actual").isLessThanOrEqualTo(Year.of(2));
	}

	@Test
	public void isGreaterThanVariable()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(0), "expected");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(0));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(1), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsBefore()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsBefore()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThan(Year.of(2));
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(Year.of(1), "expected");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsBefore()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(Year.of(2), "expected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsBefore()
	{
		Year actual = Year.of(1);
		Requirements.requireThat(actual, "actual").isGreaterThanOrEqualTo(Year.of(2));
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Year actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
