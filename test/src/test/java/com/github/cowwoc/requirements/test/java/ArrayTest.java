/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
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
			new Requirements(scope).requireThat(actual, null);
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
			new Requirements(scope).requireThat(actual, "");
		}
	}

	/**
	 * Regression test: primitiveArray.isNotNull() used to be deprecated.
	 */
	@Test
	public void isNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual =
				{
					1,
					2,
					3
				};
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
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
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
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
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
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
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
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
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
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
			new Requirements(scope).requireThat(actual, "actual").contains("element");
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
			new Requirements(scope).requireThat(actual, "actual").contains("element");
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expected");
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
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
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"notElement"
				};
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "nameOfExpected");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "expected");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfExpected");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfExpected");
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
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").doesNotContainDuplicates();
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
			new Requirements(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void isSorted()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual = {1, 2, 3};
			new Requirements(scope).requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSorted_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual = {3, 2, 1};
			new Requirements(scope).requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
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
			new Requirements(scope).requireThat(actual, "actual").length().isEqualTo(1);
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
			new Requirements(scope).requireThat(actual, "actual").length().isEqualTo(2);
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
			new Requirements(scope).requireThat(actual, "actual").length().
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
			new Requirements(scope).requireThat(actual, "actual").length().
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
			new Requirements(scope).requireThat(actual, "actual").length().isEqualTo(1, " ");
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
			new Requirements(scope).requireThat(actual, "actual").length().isNotEqualTo(2);
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
			new Requirements(scope).requireThat(actual, "actual").length().isNotEqualTo(1);
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
			new Requirements(scope).requireThat(actual, "actual").length().
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
			new Requirements(scope).requireThat(actual, "actual").length().
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
			new Requirements(scope).requireThat(actual, "actual").length().isNotEqualTo(2, " ");
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
			new Requirements(scope).requireThat(actual, "actual").length().isBetween(3, 5);
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
			new Requirements(scope).requireThat(actual, "actual").length().isBetween(3, 5);
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
			new Requirements(scope).requireThat(actual, "actual").length().isBetween(3, 5);
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
			new Requirements(scope).requireThat(actual, "actual").length().isBetween(3, 5);
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
			new Requirements(scope).requireThat(actual, "actual").length().isBetweenClosed(3, 5);
		}
	}

	@Test
	public void asCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> input = new ArrayList<>(Arrays.asList(actual));
			List<Integer> output = new ArrayList<>(requirements.requireThat(actual, "actual").
				asCollection().getActual());
			requirements.requireThat(input, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> wrongOutput = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
			List<Integer> actualOutput = new ArrayList<>(requirements.requireThat(actual, "actual").
				asCollection().getActual());
			requirements.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void asList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> input = new ArrayList<>(Arrays.asList(actual));
			List<Integer> output = requirements.requireThat(actual, "actual").asList().getActual();
			requirements.requireThat(input, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asList_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer[] actual =
				{
					1, 2, 3, 4, 5
				};
			List<Integer> wrongOutput = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
			List<Integer> actualOutput = requirements.requireThat(actual, "actual").asList().getActual();
			requirements.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
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

			new Requirements(scope).requireThat((Object) actual, "actual").isEqualTo(expected);
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

			new Requirements(scope).requireThat((Object) actual, "actual").isEqualTo(expected);
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
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			new Requirements(scope).requireThat((Object) actual, "actual").isNotEqualTo(expected);
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
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			new Requirements(scope).requireThat((Object) actual, "actual").isNotEqualTo(expected);
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

			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
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

			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
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

			new Requirements(scope).requireThat(actual, "actual").isNotEqualTo(expected);
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

			new Requirements(scope).requireThat(actual, "actual").isNotEqualTo(expected);
		}
	}

	@Test
	public void isSameObjectAs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			int[] actual = new int[]
				{
					1,
					2,
					3
				};
			new Requirements(scope).requireThat(actual, "actual").isSameObjectAs(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSameObjectAs_False()
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

			new Requirements(scope).requireThat(actual, "actual").isSameObjectAs(other, "other");
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

			new Requirements(scope).requireThat(actual, "actual").isNotSameObjectAs(other, "other");
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

			new Requirements(scope).requireThat(actual, "actual").isNotSameObjectAs(actual, "actual");
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
			new Requirements(scope).withStringConverter(int[].class, o -> "primitive[]").
				requireThat(actual, "actual").isEqualTo(expected);
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
			new Requirements(scope).withStringConverter(int[][].class, o -> "primitive[][]").
				requireThat(actual, "actual").isEqualTo(expected);
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
			new Requirements(scope).withStringConverter(Object[].class, o -> "object[]").
				requireThat(actual, "actual").isEqualTo(expected);
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : [1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_PADDING.repeat("[".length()) + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL.repeat((" 3]" + EOS_MARKER).length()) + "\n" +
				"Expected: [" + DIFF_PADDING.repeat("1,".length()) + "2, " + DIFF_PADDING.repeat("2,".length()) +
				"1, 3]" + EOS_MARKER;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3]]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_PADDING.repeat("[[".length()) + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL.repeat((" 3]]" + EOS_MARKER).length()) + "\n" +
				"Expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2, " + DIFF_PADDING.repeat("2,".length()) +
				"1, 3]]" + EOS_MARKER;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3], [4," + DIFF_PADDING.repeat("5,".length()) + " 5," +
				DIFF_PADDING.repeat("4,".length()) + " 6]]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_PADDING.repeat("[[".length()) + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL + DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL.repeat((" 3], [").length()) +
				DIFF_DELETE.repeat("4,".length()) + DIFF_INSERT.repeat("5,".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("5,".length()) + DIFF_INSERT.repeat("4,".length()) +
				DIFF_EQUAL.repeat((" 6]]" + EOS_MARKER).length()) + "\n" +
				"Expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2," + DIFF_PADDING.repeat(" 2,".length()) +
				"1, 3], [" + DIFF_PADDING.repeat("4,".length()) + "5," + DIFF_PADDING.repeat(" 5,".length()) + "4," +
				DIFF_PADDING + "6]]" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Collection<?> actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(r ->
				r.requireThat(actual, "actual").isNotNull());
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatLengthNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			new Requirements(scope).validateThat(actual, "actual").length(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsCollectionNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			new Requirements(scope).validateThat(actual, "actual").asCollection(null);
		}
	}

	@Test
	public void validateThatNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				length().isNotEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullAsCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual = null;
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asCollection().getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEmpty();
		}
	}
}
