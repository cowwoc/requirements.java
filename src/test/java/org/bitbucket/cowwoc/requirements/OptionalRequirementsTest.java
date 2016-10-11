/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class OptionalRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Optional<?> actual = Optional.empty();
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Optional<?> actual = Optional.empty();
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isPresent()
	{
		Optional<?> actual = Optional.of(5);
		Requirements.requireThat(actual, "actual").isPresent();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresent_False()
	{
		Optional<?> actual = Optional.empty();
		Requirements.requireThat(actual, "actual").isPresent();
	}

	@Test
	public void isEmpty()
	{
		Optional<?> actual = Optional.empty();
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		Optional<?> actual = Optional.of(5);
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Optional<?> actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
