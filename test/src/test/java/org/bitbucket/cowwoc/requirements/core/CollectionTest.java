/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class CollectionTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.emptyList();
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.emptyList();
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.emptyList();
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.singleton("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.singleton("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Collections.emptyList();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("notElement");
			new RequirementVerifier(scope).requireThat(actual, "actual").contains("element");
		}
	}

	@Test
	public void containsVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("notElement");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				contains("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").contains(" ");
		}
	}

	@Test
	public void containsExactly()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "expected");
		}
	}

	@Test
	public void containsExactlyVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsExactly(Arrays.asList("one", "two", "three"), " ");
		}
	}

	@Test
	public void containsAny()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"));
		}
	}

	@Test
	public void containsAnyVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAny(Arrays.asList("two", "four"), " ");
		}
	}

	@Test
	public void containsAll()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"));
		}
	}

	@Test
	public void containsAllVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				containsAll(Arrays.asList("two", "three"), " ");
		}
	}

	@Test
	public void doesNotContain()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("notElement");
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotContain("element");
		}
	}

	@Test
	public void doesNotContainVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("notElement");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContain("element", "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("notElement");
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotContain("element", " ");
		}
	}

	@Test
	public void doesNotContainAny()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"));
		}
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
		}
	}

	@Test
	public void doesNotContainAll()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"));
		}
	}

	@Test
	public void doesNotContainAllVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "four");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
		}
	}

	@Test
	public void doesNotContainDuplicates()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three");
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("one", "two", "three", "two", "four");
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotContainDuplicates();
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().
				isEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().
				isEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isEqualTo(1, " ");
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().
				isNotEqualTo(2, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().
				isNotEqualTo(1, "nameOfExpected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<String> actual = Arrays.asList("element");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isNotEqualTo(2, " ");
		}
	}

	@Test
	public void isInRange_expectedIsLowerBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3);
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isInRange_expectedIsInBounds()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4);
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void isInRange_expectedIsUpperBound()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInRange_expectedIsBelow()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Collection<Integer> actual = Arrays.asList(1, 2);
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isBetween(3, 5);
		}
	}

	@Test
	public void asArray()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			Integer[] array =
			{
				1, 2, 3, 4, 5
			};
			Collection<Integer> actual = Arrays.asList(array);
			Integer[] output = verifier.requireThat(actual, "actual").
				asArray(Integer.class).getActual();
			verifier.requireThat(array, "Input").isEqualTo(output, "Output");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asArray_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			RequirementVerifier verifier = new RequirementVerifier(scope);
			Collection<Integer> actual = Arrays.asList(new Integer[]
			{
				1, 2, 3, 4, 5
			});
			Integer[] wrongOutput =
			{
				5, 4, 3, 2, 1
			};
			Integer[] actualOutput = verifier.requireThat(actual, "actual").
				asArray(Integer.class).getActual();
			verifier.requireThat(actualOutput, "actualOutput").isEqualTo(wrongOutput, "wrongOutput");
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Collection<?> actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
