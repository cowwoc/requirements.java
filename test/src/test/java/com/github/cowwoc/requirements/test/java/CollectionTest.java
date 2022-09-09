/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
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
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new Requirements(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
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
			Collection<String> actual = Collections.singletonList("notElement");
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
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
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1, " ");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedIsNotInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1L);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isNotEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().
				isNotEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void sizeIsNotEqualTo_valueIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(null);
		}
	}

	@Test
	public void sizeIsNotEqualTo_valueIsNotInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
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
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
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
			new Requirements(scope).withAssertionsDisabled().assertThat(r ->
				r.requireThat(actual, "actual").isNotNull());
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsExactly(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsExactly(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsAny(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsAny(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsAll(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatContainsAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").containsAll(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainExactly(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainExactly(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainAny(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainAny(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainAll(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatDoesNotContainAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").doesNotContainAll(null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatSizeNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").size(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsArrayNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").asArray(null);
		}
	}

	@Test
	public void validateThatNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isEmpty().isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotEmpty().isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				contains(5).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				contains(5, "name").isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsExactly(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsExactly(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsAny(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsAny(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsAll(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContainsAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				containsAll(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContain(5).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContain(5, "name").isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainExactly(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainExactly(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainAny(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainAny(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainAll(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainAll(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContainDuplicates().isNotEqualTo(Collections.emptyList()).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				size().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullAsArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asArray(Integer.class).isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void collectionToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = List.of(1, 2, 3);
			Collection<Integer> expected = List.of(2, 1, 3);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			assert (false) : "Exception was never thrown";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]  : 1" + DIFF_PADDING.repeat("2".length()) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_DELETE.repeat("1".length()) + DIFF_INSERT.repeat("2".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected[0]: " + DIFF_PADDING.repeat("1".length()) + "2" + EOS_MARKER + "\n" +
				"\n" +
				"Actual[1]  : " + "2" + DIFF_PADDING.repeat("1".length()) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_DELETE.repeat("1".length()) + DIFF_INSERT.repeat("2".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected[1]: " + DIFF_PADDING.repeat("2".length()) + "1" + EOS_MARKER + "\n" +
				"\n" +
				"Actual[2]  : " + "3" + EOS_MARKER + "\n" +
				"Expected[2]: " + "3" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}
}
