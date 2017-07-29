/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.time.Year;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ComparableTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat(null, actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("", actual);
		}
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test
	public void isInRange()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(1);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(2);
			Year first = Year.of(0);
			Year last = Year.of(2);
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(1);
			Year first = Year.of(10);
			Year last = Year.of(20);
			new Verifiers(scope).requireThat("actual", actual).isBetween(first, last);
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).isLessThan("after", after);
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).isLessThan(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThan("actual", actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).isLessThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("after", after).isLessThan("before", before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("after", after).isLessThan(before);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo("actual", actual);
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isLessThanOrEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("after", after).
				isLessThanOrEqualTo("before", before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_expectedIsAfter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(1);
			Year after = Year.of(0);
			new Verifiers(scope).requireThat("before", before).isLessThanOrEqualTo(after);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("after", after).isGreaterThan("before", before);
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("after", after).isGreaterThan(before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThan("actual", actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("paramter", actual).isGreaterThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).isGreaterThan("after", after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).isGreaterThan(after);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo("actual", actual);
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new Verifiers(scope).requireThat("actual", actual).
				isGreaterThanOrEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).
				isGreaterThanOrEqualTo("after", after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_expectedIsBefore()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new Verifiers(scope).requireThat("before", before).isGreaterThanOrEqualTo(after);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Year actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat("actual", actual).isNotNull();
		}
	}
}
