/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.EqualityMethod;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import static com.github.cowwoc.requirements10.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ComparableTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "");
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, last);
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBetween(first, true, last, true);
		}
	}

	@Test
	public void isLessThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(before, "before").isLessThan(after, "after");
		}
	}

	@Test
	public void isLessThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(before, "before").isLessThan(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThan(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLessThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(after, "after").isLessThan(before, "before");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_actualIsGreater()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(after, "after").isLessThan(before);
		}
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isLessThanOrEqualTo(actual, "itself");
		}
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
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
			new TestValidatorsImpl(scope).requireThat(after, "after").
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
			new TestValidatorsImpl(scope).requireThat(before, "before").isLessThanOrEqualTo(after);
		}
	}

	@Test
	public void isGreaterThanVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(after, "after").isGreaterThan(before, "before");
		}
	}

	@Test
	public void isGreaterThanConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(after, "after").isGreaterThan(before);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThan(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsEqual()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isGreaterThan(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_actualIsLess()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(before, "before").isGreaterThan(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			new TestValidatorsImpl(scope).requireThat(before, "before").isGreaterThan(after);
		}
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isGreaterThanOrEqualTo(actual, "itself");
		}
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year actual = Year.of(0);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
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
			new TestValidatorsImpl(scope).requireThat(before, "before").
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
			new TestValidatorsImpl(scope).requireThat(before, "before").isGreaterThanOrEqualTo(after);
		}
	}

	@Test
	public void isComparableToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal expected = new BigDecimal("0.00");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void isComparableToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal expected = new BigDecimal("0.00");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparableToVariable_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(before, "before").isEqualTo(after, "after");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparableToConstant_actualIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(before, "before").isEqualTo(after);
		}
	}

	@Test
	public void isNotComparableToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(before, "before").isNotEqualTo(after, "after");
		}
	}

	@Test
	public void isNotComparableToConstant()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Year before = Year.of(0);
			Year after = Year.of(1);
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(before, "before").isNotEqualTo(after);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotComparableToVariable_actualIsComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal other = new BigDecimal("0.00");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isNotEqualTo(other, "other");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotComparableToConstant_actualIsComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal actual = BigDecimal.ZERO;
			BigDecimal other = new BigDecimal("0.00");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isNotEqualTo(other);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsComparableToWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isEqualTo("name", null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void multipleFailuresIsNotComparableToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isNotEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void multipleFailuresIsNotComparableToWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			validators.requireThat(actual, "actual").isNotEqualTo(null, "name");
		}
	}

	@Test
	public void multipleFailuresNullIsLessThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be less than 5",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isLessThan(5).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsLessThanWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must be less than "expected".
					expected: 5""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isLessThan(5, "expected").isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsLessThanOrEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be less than or equal to 5",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isLessThanOrEqualTo(5).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsLessThanOrEqualToWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must be less than or equal to "expected".
					expected: 5""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isLessThanOrEqualTo(5, "expected").isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsGreaterThan()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be greater than 5",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isGreaterThan(5).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsGreaterThanWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must be greater than "expected".
					expected: 5""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isGreaterThan(5, "expected").isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsGreaterThanOrEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be greater than or equal to 5",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isGreaterThanOrEqualTo(5).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsGreaterThanOrEqualToWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must be greater than or equal to "expected".
					expected: 5""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isGreaterThanOrEqualTo(5, "expected").isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsComparableTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("""
					"actual" must be equal to 5.
					actual: null""",
				"\"actual\" must be equal to 5");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			List<String> actualMessages = validators.checkIf(actual, "actual").isEqualTo(5).isEqualTo(5).
				elseGetMessages();

			validators = new TestValidatorsImpl(scope);
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsComparableToWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("""
					"actual" must be equal to "expected".
					actual  : null
					expected: 5""",
				"\"actual\" must be equal to 5");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			List<String> actualMessages = validators.checkIf(actual, "actual").isEqualTo(5, "expected")
				.isEqualTo(5).
				elseGetMessages();

			validators = new TestValidatorsImpl(scope);
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotComparableTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("""
				"actual" must be equal to 5.
				actual: null""");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			List<String> actualMessages = validators.checkIf(actual, "actual").isNotEqualTo(5).isEqualTo(5).
				elseGetMessages();

			validators = new TestValidatorsImpl(scope);
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotComparableToWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("""
				"actual" must be equal to 5.
				actual: null""");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			List<String> actualMessages = validators.checkIf(actual, "actual").isNotEqualTo(5, "name").
				isEqualTo(5).elseGetMessages();

			validators = new TestValidatorsImpl(scope);
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" is out of bounds.
					bounds: [1, 3)""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isBetween(1, 3).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsBetweenClosed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" is out of bounds.
					bounds: [1, 2]""",
				"\"actual\" must be equal to 5");
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.equalityMethod(EqualityMethod.COMPARABLE);
			}
			List<String> actualMessages = validators.checkIf(actual, "actual").
				isBetween(1, true, 2, true).isEqualTo(5).elseGetMessages();

			validators = new TestValidatorsImpl(scope);
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}