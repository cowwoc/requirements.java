/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
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
			new Verifiers(scope).requireThat(actual, null);
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
			new Verifiers(scope).requireThat(actual, "");
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
			new Verifiers(scope).requireThat(actual, "actual").isEmpty();
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
			new Verifiers(scope).requireThat(actual, "actual").isEmpty();
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
			new Verifiers(scope).requireThat(actual, "actual").isNotEmpty();
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
			new Verifiers(scope).requireThat(actual, "actual").isNotEmpty();
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
			new Verifiers(scope).requireThat(actual, "actual").contains("element");
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
			new Verifiers(scope).requireThat(actual, "actual").contains("element");
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String[] actual =
			{
				"element"
			};
			new Verifiers(scope).requireThat(actual, "actual").contains(" ");
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").doesNotContain("element");
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
			new Verifiers(scope).requireThat(actual, "actual").doesNotContain("element");
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
				doesNotContain("element", " ");
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").
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
			new Verifiers(scope).requireThat(actual, "actual").doesNotContainDuplicates();
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
			new Verifiers(scope).requireThat(actual, "actual").doesNotContainDuplicates();
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
			new Verifiers(scope).requireThat(actual, "actual").length().isEqualTo(1);
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
			new Verifiers(scope).requireThat(actual, "actual").length().isEqualTo(2);
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
			new Verifiers(scope).requireThat(actual, "actual").length().
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
			new Verifiers(scope).requireThat(actual, "actual").length().
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
			new Verifiers(scope).requireThat(actual, "actual").length().isEqualTo(1, " ");
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
			new Verifiers(scope).requireThat(actual, "actual").length().isNotEqualTo(2);
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
			new Verifiers(scope).requireThat(actual, "actual").length().isNotEqualTo(1);
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
			new Verifiers(scope).requireThat(actual, "actual").length().
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
			new Verifiers(scope).requireThat(actual, "actual").length().
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
			new Verifiers(scope).requireThat(actual, "actual").length().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
			{
				1,
				2,
				3
			};
			new Verifiers(scope).requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isInRange_expectedIsInBounds()
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
			new Verifiers(scope).requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void isInRange_expectedIsUpperBound()
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
			new Verifiers(scope).requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer[] actual =
			{
				1,
				2
			};
			new Verifiers(scope).requireThat(actual, "actual").length().isBetween(3, 5);
		}
	}

	@Test
	public void asCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			Integer[] actual =
			{
				1, 2, 3, 4, 5
			};
			List<Integer> input = new ArrayList<>(Arrays.asList(actual));
			List<Integer> output = new ArrayList<>(verifiers.requireThat(actual, "actual").
				asCollection().getActual());
			verifiers.requireThat(input, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Verifiers verifiers = new Verifiers(scope);
			Integer[] actual =
			{
				1, 2, 3, 4, 5
			};
			List<Integer> wrongOutput = new ArrayList<>(Arrays.asList(new Integer[]
			{
				5, 4, 3, 2, 1
			}));
			List<Integer> actualOutput = new ArrayList<>(verifiers.requireThat(actual, "actual").
				asCollection().getActual());
			verifiers.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Collection<?> actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
