/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class StringPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		String parameter = "value";
		Preconditions.requireThat(null, parameter);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		String parameter = "value";
		Preconditions.requireThat("", parameter);
	}

	@Test
	public void isNotEmptyTrue()
	{
		String parameter = "value";
		Preconditions.requireThat("parameter", parameter).isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		String parameter = "";
		Preconditions.requireThat("parameter", parameter).isNotEmpty();
	}

	@Test
	public void isShorterThanTrue()
	{
		String parameter = "value";
		Preconditions.requireThat("parameter", parameter).isShorterThan(10);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isShorterThanFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat("parameter", parameter).isShorterThan(10);
	}

	@Test
	public void isValidEmailTrue()
	{
		String parameter = "name@gmail.com";
		Preconditions.requireThat("parameter", parameter).isValidEmail();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isValidEmailFalse()
	{
		String parameter = "name.com";
		Preconditions.requireThat("parameter", parameter).isValidEmail();
	}

	@Test
	public void lengthInTrue()
	{
		String parameter = "123";
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat("parameter", parameter).lengthIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthInFalse()
	{
		String parameter = "1234567";
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat("parameter", parameter).lengthIn(range);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void lengthInNullParameter()
	{
		String parameter = null;
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat("parameter", parameter).lengthIn(range);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void lengthInNullRange()
	{
		String parameter = "1234567";
		Range<Integer> range = null;
		Preconditions.requireThat("parameter", parameter).lengthIn(range);
	}
}
