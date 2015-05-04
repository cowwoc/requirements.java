/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class OptionalPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Optional<?> parameter = Optional.empty();
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Optional<?> parameter = Optional.empty();
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isPresent()
	{
		Optional<?> parameter = Optional.of(5);
		Preconditions.requireThat(parameter, "parameter").isPresent();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresentFalse()
	{
		Optional<?> parameter = Optional.empty();
		Preconditions.requireThat(parameter, "parameter").isPresent();
	}

	@Test
	public void isEmpty()
	{
		Optional<?> parameter = Optional.empty();
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		Optional<?> parameter = Optional.of(5);
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Optional<?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
