/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

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
}
