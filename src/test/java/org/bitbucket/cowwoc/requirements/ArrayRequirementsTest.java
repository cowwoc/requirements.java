/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class ArrayRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		String[] actual =
		{
		};
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		String[] actual =
		{
		};
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isEmpty()
	{
		String[] actual =
		{
		};
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_actualContainsOneElement()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test
	public void isNotEmpty()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		String[] actual =
		{
		};
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test
	public void contains()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").contains("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		String[] actual =
		{
			"notElement"
		};
		Requirements.requireThat(actual, "actual").contains("element");
	}

	@Test
	public void containsVariable()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsVariable_False()
	{
		String[] actual =
		{
			"notElement"
		};
		Requirements.requireThat(actual, "actual").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedEmptyName()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").contains(" ");
	}

	@Test
	public void containsExactly()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").containsExactly(
			ImmutableList.of("one", "two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualContainsUnwantedElements()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsExactly(ImmutableList.of("one", "two"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_actualIsMissingElements()
	{
		String[] actual =
		{
			"one",
			"two"
		};
		Requirements.requireThat(actual, "actual").containsExactly(ImmutableList.of("one", "two",
			"three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_actualIsMissingElements()
	{
		String[] actual =
		{
			"one",
			"two"
		};
		Requirements.requireThat(actual, "actual").containsExactly(ImmutableList.of("one", "two",
			"three"), "expected");
	}

	@Test
	public void containsExactlyVariable()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsExactly(ImmutableList.of("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyVariable_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsExactly(ImmutableList.of("one", "two"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_expectedEmptyName()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsExactly(ImmutableList.of("one", "two", "three"), " ");
	}

	@Test
	public void containsAny()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").containsAny(ImmutableList.of("two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").containsAny(ImmutableList.of("four", "five"));
	}

	@Test
	public void containsAnyVariable()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAny(ImmutableList.of("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyVariable_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAny(ImmutableList.of("four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_expectedEmptyName()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAny(ImmutableList.of("two", "four"), " ");
	}

	@Test
	public void containsAll()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").containsAll(ImmutableList.of("two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").containsAll(ImmutableList.of("two", "four"));
	}

	@Test
	public void containsAllVariable()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAll(ImmutableList.of("two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllVariable_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAll(ImmutableList.of("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_expectedEmptyName()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			containsAll(ImmutableList.of("two", "three"), " ");
	}

	@Test
	public void doesNotContain()
	{
		String[] actual =
		{
			"notElement"
		};
		Requirements.requireThat(actual, "actual").doesNotContain("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").doesNotContain("element");
	}

	@Test
	public void doesNotContainVariable()
	{
		String[] actual =
		{
			"notElement"
		};
		Requirements.requireThat(actual, "actual").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainVariable_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_expectedEmptyName()
	{
		String[] actual =
		{
			"notElement"
		};
		Requirements.requireThat(actual, "actual").doesNotContain("element", " ");
	}

	@Test
	public void doesNotContainAny()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(ImmutableList.of("four", "five", "six"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(ImmutableList.of("three", "four", "five"));
	}

	@Test
	public void doesNotContainAnyVariable()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(ImmutableList.of("four", "five", "six"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyVariable_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(ImmutableList.of("three", "four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_expectedEmptyName()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAny(ImmutableList.of("four", "five", "six"), " ");
	}

	@Test
	public void doesNotContainAll()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(ImmutableList.of("one", "two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three",
			"four"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(ImmutableList.of("one", "two", "three"));
	}

	@Test
	public void doesNotContainAllVariable()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(ImmutableList.of("one", "two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllVariable_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three",
			"four"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(ImmutableList.of("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_expectedEmptyName()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").
			doesNotContainAll(ImmutableList.of("one", "two", "four"), " ");
	}

	@Test
	public void doesNotContainDuplicates()
	{
		String[] actual =
		{
			"one",
			"two",
			"three"
		};
		Requirements.requireThat(actual, "actual").doesNotContainDuplicates();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicates_False()
	{
		String[] actual =
		{
			"one",
			"two",
			"three",
			"two",
			"four"
		};
		Requirements.requireThat(actual, "actual").doesNotContainDuplicates();
	}

	@Test
	public void lengthIsEqualTo()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isEqualTo(2);
	}

	@Test
	public void lengthIsEqualToVariable()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualToVariable_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_expectedEmptyName()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isEqualTo(1, " ");
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(1);
	}

	@Test
	public void lengthIsNotEqualToVariable()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualToVariable_False()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_expectedEmptyName()
	{
		String[] actual =
		{
			"element"
		};
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(2, " ");
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Collection<?> actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
