/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class CollectionTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				contains("element", "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				contains("element", "expectedName");
		}
	}

	@Test
	public void containsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), " ");
		}
	}

	@Test
	public void containsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"), "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), " ");
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContain("element", "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContain("element", "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("notElement");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainExactly_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"));
		}
	}

	@Test
	public void doesNotContainExactly_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualContainsUnwantedElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two"), "unwantedName");
		}
	}

	@Test
	public void doesNotContainExactlyVariable_actualIsMissingElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwanted");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactlyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly(Arrays.asList("one", "two", "three"), "unwantedName");
		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "unwantedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_unwantedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "two", "four");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().
				isEqualTo(1, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().
				isEqualTo(2, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isEqualTo(1, " ");
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().
				isNotEqualTo(2, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().
				isNotEqualTo(1, "expectedName");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<String> actual = List.of("element");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isBetween_expectedIsLowerBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetween_expectedIsInBounds()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBetween_expectedIsBelow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isBetweenClosed_expectedIsUpperBound()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").size().isBetween(3, true, 5, true);
		}
	}

	@Test
	public void asList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			List<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			List<Integer> expected = validators.requireThat(actual, "actual").getValue();
			validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	public void asIterable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			Iterable<Integer> actual = ImmutableList.of(1, 2, 3, 4, 5);
			Iterable<Integer> expected = validators.requireThat(actual, "actual").getValue();
			validators.requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsExactly((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAny((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresContainsAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				containsAll((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainExactlyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainExactlyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainExactly((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAnyNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAnyWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAny((Collection<Integer>) null, "name");
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAllNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll((Collection<Integer>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresDoesNotContainAllWithNameNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				doesNotContainAll((Collection<Integer>) null, "name");
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be empty",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEmpty().isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be empty",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotEmpty().isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain 5",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				contains(5).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must contain the same value as "expected".
					expected: 5""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				contains(5, "expected").isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must consist of the elements [5], regardless of their order.",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsExactly(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must consist of the same elements as "expected", regardless of their order.
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsExactly(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain any of the elements present in the set [5]",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsAny(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must contain any of the elements present in "expected".
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsAny(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();

			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain all the elements present in the set [5]",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsAll(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must contain all the elements present in "expected".
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				containsAll(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain 5",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContain(5).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" may not contain the same value as "unwanted".
					unwanted: 5""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContain(5, "unwanted").isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainExactly()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not consist of the elements [5], regardless of their order.",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainExactly(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainExactlyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" may not consist of the same elements as "expected", regardless of their order.
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainExactly(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAny()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain any of the elements present in the set [5]",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainAny(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAnyWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" may not contain any of the elements present in "expected".
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainAny(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAll()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may contain some, but not all, the elements present in the set [5].",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainAll(List.of(5)).isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainAllWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" may contain some, but not all, the elements present in "expected".
					expected: [5]""",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainAll(List.of(5), "expected").
				isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContainDuplicates()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain any duplicate elements",
				"\"actual\" may not be equal to []");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContainDuplicates().isNotEqualTo(List.of()).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain 5 elements");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				size().isEqualTo(5).elseGetFailures().getMessages();
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
			List<String> expectedMessages = List.of("""
				"actual" must be equal to [2, 1, 3].
				actual: [1, 2, 3]""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEqualTo(expected).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
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