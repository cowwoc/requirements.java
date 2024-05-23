/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ArrayTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
				};
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
				};
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test
	public void containsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), "Expected");
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"), " ");

		}
	}

	@Test
	public void containsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"), "nameOfExpected");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "four"), "nameOfExpected");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"), " ");

		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two"));
		}
	}

	@Test
	public void doesNotContainExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "nameOfUnwanted");

		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "nameOfUnwanted");

		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfUnwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfUnwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");

		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three",
					"four"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfUnwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three",
					"four"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfUnwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");

		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three",
					"two",
					"four"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void isSorted()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual = {1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSorted_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual = {3, 2, 1};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isEqualTo(2);
		}
	}

	@Test
	public void lengthIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isEqualTo(1, " ");
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isNotEqualTo(1);
		}
	}

	@Test
	public void lengthIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isNotEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isNotEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"element"
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isBetween_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
				{
					1,
					2,
					3
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isBetween_expectedIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
				{
					1,
					2,
					3,
					4
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
				{
					1,
					2,
					3,
					4,
					5
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
				{
					1,
					2
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isBetweenClosed_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
				{
					1,
					2,
					3,
					4,
					5
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(3, true, 5, true);
		}
	}

	@Test
	public void asCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validator = new TestValidatorsImpl(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> input = new ArrayList<>(Arrays.asList(actual));
			List<Integer> output = new ArrayList<>(validator.requireThat(actual, "Actual").
				asCollection().getValue());
			validator.requireThat(input, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> wrongOutput = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
			List<Integer> actualOutput = new ArrayList<>(validators.requireThat(actual, "Actual").
				asCollection().getValue());
			validators.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void asList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> input = new ArrayList<>(Arrays.asList(actual));
			List<Integer> output = validators.requireThat(actual, "Actual").asList().getValue();
			validators.requireThat(input, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asList_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> wrongOutput = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
			List<Integer> actualOutput = validators.requireThat(actual, "Actual").asList().getValue();
			validators.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void objectIsEqualToArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat((Object) actual, "Actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void objectIsEqualToArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			new TestValidatorsImpl(scope).requireThat((Object) actual, "Actual").isEqualTo(expected);

		}
	}

	@Test
	public void objectIsNotEqualToArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] unwanted = new int[actual.length];
			System.arraycopy(actual, 0, unwanted, 0, actual.length);
			unwanted[0] = 4;

			new TestValidatorsImpl(scope).requireThat((Object) actual, "Actual").isNotEqualTo(unwanted);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void objectIsNotEqualToArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] unwanted = new int[actual.length];
			System.arraycopy(actual, 0, unwanted, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat((Object) actual, "Actual").isNotEqualTo(unwanted);
		}
	}

	@Test
	public void arrayIsEqualToObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void arrayIsEqualToObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
		}
	}

	@Test
	public void arrayIsNotEqualToObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void arrayIsNotEqualToObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEqualTo(expected);
		}
	}

	@Test
	public void isSameReferenceAs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSameReferenceAs(actual, "itself");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSameReferenceAs_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] other = new int[actual.length];
			System.arraycopy(actual, 0, other, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSameReferenceAs(other, "other");
		}
	}

	@Test
	public void isNotSameObjectAs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] other = new int[actual.length];
			System.arraycopy(actual, 0, other, 0, actual.length);

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotSameReferenceAs(other, "other");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotSameObjectAs_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotSameReferenceAs(actual, "Actual");
		}
	}

	@Test
	public void nullStringMapper()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);
			String actual = validators.configuration().stringMappers().toString(null);
			String expected = "null";
			validators.requireThat(actual, "Actual").isEqualTo(expected);
		}
	}

	@Test
	public void primitiveToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			int[] expected = new int[]
				{
					2,
					1,
					3
				};
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(int[].class, o -> "primitive[]");
			}
			validators.requireThat(actual, "Actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains("primitive[]")) : "Actual:\n" + actualMessage;
		}
	}

	@Test
	public void multiDimensionalToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[][] actual = new int[][]
				{
					{
						1,
						2,
						3
					},
					{
						4,
						5,
						6
					}
				};
			int[][] expected = new int[][]
				{
					{
						2,
						1,
						3
					},
					{
						5,
						4,
						6
					}
				};
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(int[][].class, o -> "primitive[][]");
			}
			validators.requireThat(actual, "Actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains("primitive[][]")) : "Actual:\n" + actualMessage;
		}
	}

	@Test
	public void objectToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new String[]
				{
					"1",
					"2",
					"3"
				};
			Object expected = new String[]
				{
					"2",
					"1",
					"3"
				};
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(Object[].class, o -> "object[]");
			}
			validators.requireThat(actual, "Actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains("object[]")) : "Actual:\n" + actualMessage;
		}
	}

	@Test
	public void objectArrayToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object[] actual = new Integer[]
				{
					1,
					2,
					3
				};
			Object[] expected = new Integer[]
				{
					2,
					1,
					3
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = """
				"Actual" must be equal to [2, 1, 3].
				Actual: [1, 2, 3]""";
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void multiDimensionalObjectArrayToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object[][] actual = new Integer[][]
				{
					{
						1,
						2,
						3
					}
				};
			Object[] expected = new Integer[][]
				{
					{
						2,
						1,
						3
					}
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "\"Actual\" had an unexpected value.\n" +
				"\n" +
				"Expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2, " +
				DIFF_PADDING.repeat("2,".length()) +
				"1, 3]]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_PADDING.repeat("[[".length()) +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) +
				DIFF_EQUAL.repeat((" 3]]" + EOS_MARKER).length()) + "\n" +
				"Actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3]]" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void multiDimensionalToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[][] actual = new int[][]
				{
					{
						1,
						2,
						3
					},
					{
						4,
						5,
						6
					}
				};
			int[][] expected = new int[][]
				{
					{
						2,
						1,
						3
					},
					{
						5,
						4,
						6
					}
				};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "\"Actual\" had an unexpected value.\n" +
				"\n" +
				"Expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2," +
				DIFF_PADDING.repeat(" 2,".length()) +
				"1, 3], [" + DIFF_PADDING.repeat("4,".length()) + "5," +
				DIFF_PADDING.repeat(" 5,".length()) + "4," +
				DIFF_PADDING + "6]]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_PADDING.repeat("[[".length()) +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL.repeat((" 3], [").length()) +
				DIFF_DELETE.repeat("4,".length()) + DIFF_INSERT.repeat("5,".length()) +
				DIFF_EQUAL +
				DIFF_DELETE.repeat("5,".length()) + DIFF_INSERT.repeat("4,".length()) +
				DIFF_EQUAL.repeat((" 6]]" + EOS_MARKER).length()) + "\n" +
				"Actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3], [4," +
				DIFF_PADDING.repeat("5,".length()) + " 5," +
				DIFF_PADDING.repeat("4,".length()) + " 6]]" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void multipleFailuresNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" may not contain 5 elements");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				length().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullAsCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				asCollection().elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				contains("\"Actual\" may not be null");
		}
	}
}
