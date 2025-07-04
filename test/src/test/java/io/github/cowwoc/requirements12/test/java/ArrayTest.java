/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.java;

import io.github.cowwoc.requirements12.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.test.TestValidators;
import io.github.cowwoc.requirements12.test.TestValidatorsImpl;
import io.github.cowwoc.requirements12.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.github.cowwoc.requirements12.java.TerminalEncoding.NONE;
import static io.github.cowwoc.requirements12.java.internal.message.diff.DiffConstants.DIFF_DELETE;
import static io.github.cowwoc.requirements12.java.internal.message.diff.DiffConstants.DIFF_EQUAL;
import static io.github.cowwoc.requirements12.java.internal.message.diff.DiffConstants.DIFF_INSERT;
import static io.github.cowwoc.requirements12.java.internal.message.diff.DiffConstants.EOS_MARKER;
import static io.github.cowwoc.requirements12.java.internal.message.diff.TextOnly.DIFF_PADDING;
import static io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder.DIFF_LEGEND;

@SuppressWarnings("ConstantConditions")
public final class ArrayTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
				};
			validators.requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
				};
			validators.requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
				};
			validators.requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
				};
			validators.requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"notElement"
				};
			validators.requireThat(actual, "actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").
				contains("element", "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"notElement"
				};
			validators.requireThat(actual, "actual").
				contains("element", "expectedName");
		}
	}

	@Test
	public void containsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expected");
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), " ");

		}
	}

	@Test
	public void containsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), "expectedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"), "expectedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), " ");

		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"notElement"
				};
			validators.requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"notElement"
				};
			validators.requireThat(actual, "actual").
				doesNotContain("element", "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").
				doesNotContain("element", "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"notElement"
				};
			validators.requireThat(actual, "actual").
				doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"));
		}
	}

	@Test
	public void doesNotContainExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "unwantedName");

		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwanted");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwantedName");

		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "unwantedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "unwantedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");

		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three",
					"four"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "unwantedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three",
					"four"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "unwantedName");

		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");

		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three"
				};
			validators.requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"one",
					"two",
					"three",
					"two",
					"four"
				};
			validators.requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void isSorted()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual = {1, 2, 3};
			validators.requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSorted_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual = {3, 2, 1};
			validators.requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isEqualTo(2);
		}
	}

	@Test
	public void lengthIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().
				isEqualTo(1, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().
				isEqualTo(2, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isEqualTo(1, " ");
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isNotEqualTo(1);
		}
	}

	@Test
	public void lengthIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isNotEqualTo(2, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().
				isNotEqualTo(1, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual =
				{
					"element"
				};
			validators.requireThat(actual, "actual").length().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isBetween_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual =
				{
					1,
					2,
					3
				};
			validators.requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isBetween_expectedIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual =
				{
					1,
					2,
					3,
					4
				};
			validators.requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual =
				{
					1,
					2,
					3,
					4,
					5
				};
			validators.requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual =
				{
					1,
					2
				};
			validators.requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isBetweenClosed_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Integer[] actual =
				{
					1,
					2,
					3,
					4,
					5
				};
			validators.requireThat(actual, "actual").length().isBetween(3, true, 5, true);
		}
	}

	@Test
	public void objectIsEqualToArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			validators.requireThat((Object) actual, "actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void objectIsEqualToArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			validators.requireThat((Object) actual, "actual").isEqualTo(expected);

		}
	}

	@Test
	public void objectIsNotEqualToArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] unwanted = new int[actual.length];
			System.arraycopy(actual, 0, unwanted, 0, actual.length);
			unwanted[0] = 4;

			validators.requireThat((Object) actual, "actual").isNotEqualTo(unwanted);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void objectIsNotEqualToArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] unwanted = new int[actual.length];
			System.arraycopy(actual, 0, unwanted, 0, actual.length);

			validators.requireThat((Object) actual, "actual").isNotEqualTo(unwanted);
		}
	}

	@Test
	public void arrayIsEqualToObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			validators.requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void arrayIsEqualToObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			validators.requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test
	public void arrayIsNotEqualToObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);
			expected[0] = 4;

			validators.requireThat(actual, "actual").isNotEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void arrayIsNotEqualToObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected = new int[actual.length];
			System.arraycopy(actual, 0, expected, 0, actual.length);

			validators.requireThat(actual, "actual").isNotEqualTo(expected);
		}
	}

	@Test
	public void isSameReferenceAs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			validators.requireThat(actual, "actual").isReferenceEqualTo(actual, "itself");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSameReferenceAs_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] other = new int[actual.length];
			System.arraycopy(actual, 0, other, 0, actual.length);

			validators.requireThat(actual, "actual").isReferenceEqualTo(other, "other");
		}
	}

	@Test
	public void isNotSameObjectAs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] other = new int[actual.length];
			System.arraycopy(actual, 0, other, 0, actual.length);

			validators.requireThat(actual, "actual").isReferenceNotEqualTo(other, "other");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotSameObjectAs_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};

			validators.requireThat(actual, "actual").isReferenceNotEqualTo(actual, "actual");
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
			validators.requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test
	public void primitiveToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[] actual =
				{
					1,
					2,
					3
				};
			int[] expected =
				{
					2,
					1,
					3
				};
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(int[].class, (value, seen) -> "primitive[]");
			}
			validators.requireThat(actual, "actual").isEqualTo(expected);
			assert false : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert actualMessage.contains("primitive[]") : "actual:\n" + actualMessage;
		}
	}

	@Test
	public void multiDimensionalToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[][] actual =
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
			int[][] expected =
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
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(int[][].class, (value, seen) -> "primitive[][]");
			}
			validators.requireThat(actual, "actual").isEqualTo(expected);
			assert false : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert actualMessage.contains("primitive[][]") : "actual:\n" + actualMessage;
		}
	}

	@Test
	public void objectToStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

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
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.stringMappers().put(Object[].class, (value, seen) -> "object[]");
			}
			validators.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert actualMessage.contains("object[]") : "actual:\n" + actualMessage;
		}
	}

	@Test
	public void objectArrayToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Object[] actual =
				{
					1,
					2,
					3
				};
			Object[] expected =
				{
					2,
					1,
					3
				};
			List<String> expectedMessages = List.of("""
				"actual" must be equal to [2, 1, 3].
				actual: [1, 2, 3]""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEqualTo(expected).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multiDimensionalObjectArrayToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Object[][] actual =
				{
					new Integer[]
						{
							1,
							2,
							3
						}
				};
			Object[] expected =
				{
					new Integer[]
						{
							2,
							1,
							3
						}
				};
			List<String> expectedMessages = List.of("\"actual\" had an unexpected value.\n" +
				"\n" +
				"actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3]]" + EOS_MARKER + "\n" +
				"diff    : " + DIFF_PADDING.repeat("[[".length()) +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) +
				DIFF_EQUAL.repeat((" 3]]" + EOS_MARKER).length()) + "\n" +
				"expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2, " +
				DIFF_PADDING.repeat("2,".length()) +
				"1, 3]]" + EOS_MARKER + "\n" +
				DIFF_LEGEND);
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEqualTo(expected).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multiDimensionalToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			int[][] actual =
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
			int[][] expected =
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
			List<String> expectedMessages = List.of("\"actual\" had an unexpected value.\n" +
				"\n" +
				"actual  : [[1," + DIFF_PADDING.repeat("2,".length()) + " 2," +
				DIFF_PADDING.repeat("1,".length()) + " 3], [4," +
				DIFF_PADDING.repeat("5,".length()) + " 5," +
				DIFF_PADDING.repeat("4,".length()) + " 6]]" + EOS_MARKER + "\n" +
				"diff    : " + DIFF_PADDING.repeat("[[".length()) +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("1,".length()) +
				DIFF_INSERT.repeat("2,".length()) + DIFF_EQUAL.repeat(" 3], [".length()) +
				DIFF_DELETE.repeat("4,".length()) + DIFF_INSERT.repeat("5,".length()) +
				DIFF_EQUAL +
				DIFF_DELETE.repeat("5,".length()) + DIFF_INSERT.repeat("4,".length()) +
				DIFF_EQUAL.repeat((" 6]]" + EOS_MARKER).length()) + "\n" +
				"expected: [[" + DIFF_PADDING.repeat("1,".length()) + "2," +
				DIFF_PADDING.repeat(" 2,".length()) +
				"1, 3], [" + DIFF_PADDING.repeat("4,".length()) + "5," +
				DIFF_PADDING.repeat(" 5,".length()) + "4," +
				DIFF_PADDING + "6]]" + EOS_MARKER + "\n" +
				DIFF_LEGEND);
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEqualTo(expected).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			String[] actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain 5 elements");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				length().isNotEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}