/*
 * Copyright 2014 Gili.
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
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Collection<?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
