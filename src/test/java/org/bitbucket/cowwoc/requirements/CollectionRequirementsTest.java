/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class CollectionRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Collection<String> actual = Collections.emptyList();
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Collection<String> actual = Collections.emptyList();
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isEmpty()
	{
		Collection<String> actual = Collections.emptyList();
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		Collection<String> actual = Collections.singleton("element");
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test
	public void isNotEmpty()
	{
		Collection<String> actual = Collections.singleton("element");
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		Collection<String> actual = Collections.emptyList();
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test
	public void contains()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").contains("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		Collection<String> actual = Arrays.asList("notElement");
		Requirements.requireThat(actual, "actual").contains("element");
	}

	@Test
	public void containsVariable()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		Collection<String> actual = Arrays.asList("notElement");
		Requirements.requireThat(actual, "actual").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").contains(" ");
	}

	@Test
	public void containsExactly()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").containsExactly(
			Arrays.asList("one", "two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsExactly(Arrays.asList("one", "two"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		Collection<String> actual = Arrays.asList("one", "two");
		Requirements.requireThat(actual, "actual").containsExactly(Arrays.asList("one",
			"two",
			"three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		Collection<String> actual = Arrays.asList("one", "two");
		Requirements.requireThat(actual, "actual").containsExactly(Arrays.asList("one",
			"two",
			"three"), "expected");
	}

	@Test
	public void containsExactlyVariable()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsExactly(Arrays.asList("one", "two", "three"), " ");
	}

	@Test
	public void containsAny()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").containsAny(Arrays.asList("two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAny(Arrays.asList("four", "five"));
	}

	@Test
	public void containsAnyVariable()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAny(Arrays.asList("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAny(Arrays.asList("four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAny(Arrays.asList("two", "four"), " ");
	}

	@Test
	public void containsAll()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAll(Arrays.asList("two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").containsAll(Arrays.asList("two", "four"));
	}

	@Test
	public void containsAllVariable()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAll(Arrays.asList("two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAll(Arrays.asList("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			containsAll(Arrays.asList("two", "three"), " ");
	}

	@Test
	public void doesNotContain()
	{
		Collection<String> actual = Arrays.asList("notElement");
		Requirements.requireThat(actual, "actual").doesNotContain("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").doesNotContain("element");
	}

	@Test
	public void doesNotContainVariable()
	{
		Collection<String> actual = Arrays.asList("notElement");
		Requirements.requireThat(actual, "actual").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("notElement");
		Requirements.requireThat(actual, "actual").doesNotContain("element", " ");
	}

	@Test
	public void doesNotContainAny()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(Arrays.asList("four", "five", "six"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(Arrays.asList("three", "four", "five"));
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
	}

	@Test
	public void doesNotContainAll()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(Arrays.asList("one", "two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three", "four");
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(Arrays.asList("one", "two", "three"));
	}

	@Test
	public void doesNotContainAllVariable()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three", "four");
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
	}

	@Test
	public void doesNotContainDuplicates()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three");
		Requirements.requireThat(actual, "actual").doesNotContainDuplicates();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		Collection<String> actual = Arrays.asList("one", "two", "three", "two", "four");
		Requirements.requireThat(actual, "actual").doesNotContainDuplicates();
	}

	@Test
	public void sizeIsEqualTo()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isEqualTo(2);
	}

	@Test
	public void sizeIsEqualToVariable()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToVariable_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isEqualTo(1, " ");
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isNotEqualTo(1);
	}

	@Test
	public void sizeIsNotEqualToVariable()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isNotEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToVariable_False()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isNotEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_expectedEmptyName()
	{
		Collection<String> actual = Arrays.asList("element");
		Requirements.requireThat(actual, "actual").size().isNotEqualTo(2, " ");
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Collection<?> actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
