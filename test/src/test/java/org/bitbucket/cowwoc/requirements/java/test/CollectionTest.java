/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import com.google.common.collect.ImmutableList;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class CollectionTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singleton("element");
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singleton("element");
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("notElement");
			new Requirements(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("notElement");
			new Requirements(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test
	public void containsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), " ");
		}
	}

	@Test
	public void containsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), " ");
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("notElement");
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("notElement");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("notElement");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"));
		}
	}

	@Test
	public void doesNotContainExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "nameOfExpected");
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new Requirements(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "two", "four");
			new Requirements(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedIsNotInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1L);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isNotEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isNotEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(2, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_valueIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_valueIsNotInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(1L);
		}
	}

	@Test
	public void isBetween_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3);
			new Requirements(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetween_expectedIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4);
			new Requirements(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new Requirements(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2);
			new Requirements(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetweenClosed_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new Requirements(scope).requireThat(actual, "actual").size().isBetweenClosed(3, 5);
		}
	}

	@Test
	public void asList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			List<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			List<Integer> expected = requirements.requireThat(actual, "actual").getActual();
			requirements.requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void asIterable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Iterable<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			Iterable<Integer> expected = requirements.requireThat(actual, "actual").getActual();
			requirements.requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void asArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Integer[] array =
				{
					1, 2, 3, 4, 5
				};
			Collection<Integer> actual = Arrays.asList(array);
			Integer[] output = requirements.requireThat(actual, "actual").
				asArray(Integer.class).getActual();
			requirements.requireThat(array, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Requirements requirements = new Requirements(scope);
			Collection<Integer> actual = Arrays.asList(new Integer[]
				{
					1, 2, 3, 4, 5
				});
			Integer[] wrongOutput =
				{
					5, 4, 3, 2, 1
				};
			Integer[] actualOutput = requirements.requireThat(actual, "actual").
				asArray(Integer.class).getActual();
			requirements.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Collection<?> actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
