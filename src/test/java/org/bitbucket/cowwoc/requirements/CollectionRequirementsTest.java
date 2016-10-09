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
		Collection<String> parameter = Collections.emptyList();
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Collection<String> parameter = Collections.emptyList();
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isEmptyTrue()
	{
		Collection<String> parameter = Collections.emptyList();
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		Collection<String> parameter = Collections.singleton("element");
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		Collection<String> parameter = Collections.singleton("element");
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		Collection<String> parameter = Collections.emptyList();
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void containsTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").contains("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsFalse()
	{
		Collection<String> parameter = Collections.singletonList("notElement");
		Requirements.requireThat(parameter, "parameter").contains("element");
	}

	@Test
	public void containsWithNameTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsWithNameFalse()
	{
		Collection<String> parameter = Collections.singletonList("notElement");
		Requirements.requireThat(parameter, "parameter").contains("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsWithEmptyName()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").contains(" ");
	}

	@Test
	public void containsExactlyTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").containsExactly(
			Arrays.asList("one", "two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyFalseUnwantedElements()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsExactly(Arrays.asList("one", "two"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyFalseMissingElements()
	{
		Collection<String> parameter = Arrays.asList("one", "two");
		Requirements.requireThat(parameter, "parameter").containsExactly(Arrays.asList("one", "two",
			"three"));
	}

	@Test
	public void containsExactlyWithNameTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsExactly(Arrays.asList("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyWithNameFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsExactly(Arrays.asList("one", "two"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactlyWithEmptyName()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsExactly(Arrays.asList("one", "two", "three"), " ");
	}

	@Test
	public void containsAnyTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").containsAny(Arrays.asList("two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").containsAny(Arrays.asList("four", "five"));
	}

	@Test
	public void containsAnyWithNameTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAny(Arrays.asList("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyWithNameFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAny(Arrays.asList("four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyWithEmptyName()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAny(Arrays.asList("two", "four"), " ");
	}

	@Test
	public void containsAllTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").containsAll(Arrays.asList("two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").containsAll(Arrays.asList("two", "four"));
	}

	@Test
	public void containsAllWithNameTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAll(Arrays.asList("two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllWithNameFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAll(Arrays.asList("two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllWithEmptyName()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			containsAll(Arrays.asList("two", "three"), " ");
	}

	@Test
	public void doesNotContainTrue()
	{
		Collection<String> parameter = Collections.singletonList("notElement");
		Requirements.requireThat(parameter, "parameter").doesNotContain("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").doesNotContain("element");
	}

	@Test
	public void doesNotContainWithNameTrue()
	{
		Collection<String> parameter = Collections.singletonList("notElement");
		Requirements.requireThat(parameter, "parameter").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainWithNameFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").doesNotContain("element", "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainWithEmptyName()
	{
		Collection<String> parameter = Collections.singletonList("notElement");
		Requirements.requireThat(parameter, "parameter").doesNotContain("element", " ");
	}

	@Test
	public void doesNotContainAnyTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAny(Arrays.asList("four", "five", "six"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAny(Arrays.asList("three", "four", "five"));
	}

	@Test
	public void doesNotContainAnyWithNameTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAny(Arrays.asList("four", "five", "six"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyWithNameFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAny(Arrays.asList("three", "four", "five"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyWithEmptyName()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAny(Arrays.asList("four", "five", "six"), " ");
	}

	@Test
	public void doesNotContainAllTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAll(Arrays.asList("one", "two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three", "four");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAll(Arrays.asList("one", "two", "three"));
	}

	@Test
	public void doesNotContainAllWithNameTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAll(Arrays.asList("one", "two", "four"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllWithNameFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three", "four");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAll(Arrays.asList("one", "two", "three"), "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllWithEmptyName()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").
			doesNotContainAll(Arrays.asList("one", "two", "four"), " ");
	}

	@Test
	public void doesNotContainDuplicatesTrue()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three");
		Requirements.requireThat(parameter, "parameter").doesNotContainDuplicates();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainDuplicatesFalse()
	{
		Collection<String> parameter = Arrays.asList("one", "two", "three", "two", "four");
		Requirements.requireThat(parameter, "parameter").doesNotContainDuplicates();
	}

	@Test
	public void sizeIsEqualToTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(2);
	}

	@Test
	public void sizeIsEqualToWithNameTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToWithNameFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToWithEmptyName()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(1, " ");
	}

	@Test
	public void sizeIsNotEqualToTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(1);
	}

	@Test
	public void sizeIsNotEqualToWithNameTrue()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(2, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToWithNameFalse()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(1, "nameOfExpected");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToWithEmptyName()
	{
		Collection<String> parameter = Collections.singletonList("element");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(2, " ");
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Collection<?> parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}
}
