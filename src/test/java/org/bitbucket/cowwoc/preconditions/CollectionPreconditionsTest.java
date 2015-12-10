/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class CollectionPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Collection<String> parameter = Collections.emptyList();
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Collection<String> parameter = Collections.emptyList();
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isEmptyTrue()
	{
		Collection<String> parameter = Collections.emptyList();
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		Collection<String> parameter = Collections.singleton("element");
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		Collection<String> parameter = Collections.singleton("element");
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		Collection<String> parameter = Collections.emptyList();
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void containsTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").contains("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsFalse()
	{
		Collection<String> parameter = ImmutableList.of("notElement");
		Preconditions.requireThat(parameter, "parameter").contains("element");
	}

	@Test
	public void containsWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").contains("element", "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("notElement");
		Preconditions.requireThat(parameter, "parameter").contains("element", "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").contains(" ");
	}

	@Test
	public void containsAnyTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").containsAny(ImmutableList.of("two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").containsAny(ImmutableList.of("four", "five"));
	}

	@Test
	public void containsAnyWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAny(ImmutableList.of("two", "four"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAny(ImmutableList.of("four", "five"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAnyWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAny(ImmutableList.of("two", "four"), " ");
	}

	@Test
	public void containsAllTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").containsAll(ImmutableList.of("two", "three"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").containsAll(ImmutableList.of("two", "four"));
	}

	@Test
	public void containsAllWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAll(ImmutableList.of("two", "three"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAll(ImmutableList.of("two", "four"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAllWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			containsAll(ImmutableList.of("two", "three"), " ");
	}

	@Test
	public void doesNotContainTrue()
	{
		Collection<String> parameter = ImmutableList.of("notElement");
		Preconditions.requireThat(parameter, "parameter").doesNotContain("element");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").doesNotContain("element");
	}

	@Test
	public void doesNotContainWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("notElement");
		Preconditions.requireThat(parameter, "parameter").doesNotContain("element", "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").doesNotContain("element", "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("notElement");
		Preconditions.requireThat(parameter, "parameter").doesNotContain("element", " ");
	}

	@Test
	public void doesNotContainAnyTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAny(ImmutableList.of("four", "five", "six"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAny(ImmutableList.of("three", "four", "five"));
	}

	@Test
	public void doesNotContainAnyWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAny(ImmutableList.of("four", "five", "six"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAny(ImmutableList.of("three", "four", "five"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAnyWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAny(ImmutableList.of("four", "five", "six"), " ");
	}

	@Test
	public void doesNotContainAllTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAll(ImmutableList.of("one", "two", "four"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three", "four");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAll(ImmutableList.of("one", "two", "three"));
	}

	@Test
	public void doesNotContainAllWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAll(ImmutableList.of("one", "two", "four"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three", "four");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAll(ImmutableList.of("one", "two", "three"), "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAllWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("one", "two", "three");
		Preconditions.requireThat(parameter, "parameter").
			doesNotContainAll(ImmutableList.of("one", "two", "four"), " ");
	}

	@Test
	public void sizeIsEqualToTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(2);
	}

	@Test
	public void sizeIsEqualToWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(1, "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(2, "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(1, " ");
	}

	@Test
	public void sizeIsNotEqualToTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(1);
	}

	@Test
	public void sizeIsNotEqualToWithNameTrue()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(2, "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToWithNameFalse()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(1, "name");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToWithEmptyName()
	{
		Collection<String> parameter = ImmutableList.of("element");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(2, " ");
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Collection<?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
