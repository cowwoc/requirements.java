/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Arrays;
import java.util.Random;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ObjectRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isEquals()
	{
		String actual = "actual";
		Requirements.requireThat(actual, "actual").isEqualTo(actual);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_False()
	{
		String actual = "actual";
		Requirements.requireThat(actual, "actual").isEqualTo("expected");
	}

	@Test
	public void isNotEquals()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, "actual").isNotEqualTo(new Object());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEquals_False()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, "actual").isNotEqualTo(actual);
	}

	@Test
	public void isInCollection()
	{
		String actual = "value";

		// Make sure that the collection uses equals()
		@SuppressWarnings("RedundantStringConstructorCall")
		String equivalent = new String(actual);

		Requirements.requireThat(actual, "actual").
			isIn(Arrays.asList("first", equivalent, "third"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInCollection_False()
	{
		String actual = "value";
		Requirements.requireThat(actual, "actual").
			isIn(Arrays.asList("first", "second", "third"));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_actualIsNull()
	{
		Random actual = null;
		Requirements.requireThat(actual, "actual").isInstanceOf(Random.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedIsNull()
	{
		Random actual = new Random();
		Requirements.requireThat(actual, "actual").isInstanceOf(null);
	}

	@Test
	public void isInstanceOf()
	{
		Random actual = new Random();
		Requirements.requireThat(actual, "actual").isInstanceOf(Random.class).
			isInstanceOf(Object.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		Random actual = new Random();
		Requirements.requireThat(actual, "actual").isInstanceOf(String.class);
	}

	@Test
	public void isNull()
	{
		Object actual = null;
		Requirements.requireThat(actual, "actual").isNull();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, "actual").isNull();
	}

	@Test
	public void isNotNull()
	{
		Object actual = new Object();
		Requirements.requireThat(actual, "actual").isNotNull();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		Object actual = null;
		Requirements.requireThat(actual, "actual").isNotNull();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNull_CustomException()
	{
		Object actual = null;
		Requirements.requireThat(actual, "actual").withException(IllegalStateException.class).
			isNotNull();
	}

	/**
	 * BUG: {@code Requirements.withException(Class<? extends RuntimeException>)} was throwing a
	 * {@code ClassCastException} if the instance was anything other than
	 * {@code ObjectRequirementsImpl}.
	 */
	@Test
	public void customExceptionRequirementSubclass()
	{
		int actual = 5;
		Requirements.requireThat(actual, "actual").withException(IllegalStateException.class).
			isNotNull();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Object actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
