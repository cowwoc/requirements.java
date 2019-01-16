/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Year;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ComparableTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isBetween_actualIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(1);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(2);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_actualIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(1);
			Year first = Year.of(10);
			Year last = Year.of(20);
			new Requirements(scope).requireThat(actual, "actual").isBetween(first, last);
		}
	}

	@Test
	public void isBetweenClosed_actualIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(2);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Requirements(scope).requireThat(actual, "actual").isBetweenClosed(first, last);
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isLessThan(after, "after");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isLessThan(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isLessThan(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").isLessThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(after, "after").isLessThan(before, "before");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(after, "after").isLessThan(before);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(actual, "actual");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(after, "after").
				isLessThanOrEqualTo(before, "before");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(1);
			Year after = Year.of(0);
			new Requirements(scope).requireThat(before, "before").isLessThanOrEqualTo(after);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(after, "after").isGreaterThan(before, "before");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(after, "after").isGreaterThan(before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isGreaterThan(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").isGreaterThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isGreaterThan(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isGreaterThan(after);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(actual, "actual");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Requirements(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").
				isGreaterThanOrEqualTo(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_actualIsLessThan()
	{

		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isGreaterThanOrEqualTo(after);
		}
	}

	@Test
	public void isComparableToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal expected = new BigDecimal("0.00");
			new Requirements(scope).requireThat(actual, "actual").
				isComparableTo(expected, "expected");
		}
	}

	@Test
	public void isComparableToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal expected = new BigDecimal("0.00");
			new Requirements(scope).requireThat(actual, "actual").
				isComparableTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparableToVariable_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").
				isComparableTo(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparableToConstant_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isComparableTo(after);
		}
	}

	@Test
	public void isNotComparableToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").
				isNotComparableTo(after, "after");
		}
	}

	@Test
	public void isNotComparableToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Requirements(scope).requireThat(before, "before").isNotComparableTo(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotComparableToVariable_actualIsComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal other = new BigDecimal("0.00");
			new Requirements(scope).requireThat(actual, "actual").
				isNotComparableTo(other, "other");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotComparableToConstant_actualIsComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal other = new BigDecimal("0.00");
			new Requirements(scope).requireThat(actual, "actual").isNotComparableTo(other);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Year actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
