/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class CollectionTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singleton("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singleton("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.emptyList();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test
	public void containsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny(Arrays.asList("two", "four"), " ");
		}
	}

	@Test
	public void containsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll(Arrays.asList("two", "three"), " ");
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two"));
		}
	}

	@Test
	public void doesNotContainExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "nameOfUnwanted");
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "nameOfUnwanted");
		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfUnwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "two", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().
				isEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().
				isEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isEqualTo(1, " ");
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().
				isNotEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().
				isNotEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Collections.singletonList("element");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isBetween_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetween_expectedIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetweenClosed_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isBetween(3, true, 5, true);
		}
	}

	@Test
	public void asList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			List<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			List<Integer> expected = validators.requireThat(actual, "Actual").getValue();
			validators.requireThat(actual, "Actual").isEqualTo(expected, "Expected");
		}
	}

	@Test
	public void asIterable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Iterable<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			Iterable<Integer> expected = validators.requireThat(actual, "Actual").getValue();
			validators.requireThat(actual, "Actual").isEqualTo(expected, "Expected");
		}
	}

	@Test
	public void asArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Integer[] array =
				{
					1, 2, 3, 4, 5
				};
			Collection<Integer> actual = Arrays.asList(array);
			Integer[] output = validators.requireThat(actual, "Actual").
				asArray(Integer.class).getValue();
			validators.requireThat(array, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			Integer[] wrongOutput =
				{
					5, 4, 3, 2, 1
				};
			Integer[] actualOutput = validators.requireThat(actual, "Actual").
				asArray(Integer.class).getValue();
			validators.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsExactly((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAny((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				containsAll((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainExactly((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAny((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				doesNotContainAll((Collection<Integer>) null, "name");
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isEmpty().isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotEmpty().isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				contains(5).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				contains(5, "name").isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsExactly(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsExactly(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsAny(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsAny(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();

			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsAll(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				containsAll(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContain(5).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContain(5, "name").isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainExactly(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).
				elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainExactly(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainAny(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainAny(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainAll(Collections.singletonList(5)).isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainAll(Collections.singletonList(5), "name").
				isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContainDuplicates().isNotEqualTo(Collections.emptyList()).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" must contain 5 elements");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				size().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullAsArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.asArray() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				asArray(Integer.class).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void collectionToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = List.of(1, 2, 3);
			Collection<Integer> expected = List.of(2, 1, 3);
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

	public void doesNotContainMixedNullity_IsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Map<String, Object> nameToValue = new HashMap<>();
			nameToValue.put("first", null);
			nameToValue.put("second", null);
			validators.requireThat(nameToValue, "nameToValue").values().containsSameNullity();
		}
	}

	public void doesNotContainMixedNullity_IsNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Map<String, Object> nameToValue = Map.of("first", 1,
				"second", 10);
			validators.requireThat(nameToValue, "nameToValue").values().containsSameNullity();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainMixedNullity_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Map<String, Object> nameToValue = new HashMap<>();
			nameToValue.put("first", 1);
			nameToValue.put("second", null);
			validators.requireThat(nameToValue, "nameToValue").values().containsSameNullity();
		}
	}
}