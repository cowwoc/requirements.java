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
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isNotEmptyTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		String parameter = "";
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void hasMinimumLengthTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").hasMinimumLength(parameter.length());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void hasMinimumLengthFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat(parameter, "parameter").hasMinimumLength(parameter.length() + 1);
	}

	@Test
	public void hasLengthTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").hasLength(parameter.length());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void hasLengthFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat(parameter, "parameter").hasLength(parameter.length() + 1);
	}

	@Test
	public void hasMaximumLengthTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").hasMaximumLength(parameter.length());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void hasMaximumLengthFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat(parameter, "parameter").hasMaximumLength(parameter.length() - 1);
	}

	@Test
	public void isEmailFormatTrue()
	{
		String parameter = "name@gmail.com";
		Preconditions.requireThat(parameter, "parameter").isEmailFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmailFormatFalse()
	{
		String parameter = "name.com";
		Preconditions.requireThat(parameter, "parameter").isEmailFormat();
	}

	@Test
	public void isIpAddressFormatTrueIpV4()
	{
		String parameter = "1.2.3.4";
		Preconditions.requireThat(parameter, "parameter").isIpAddressFormat();
	}

	@Test
	public void isIpAddressFormatTrueIpV6()
	{
		String parameter = "0000:0000:0000:0000:0000:0000:192.168.0.1";
		Preconditions.requireThat(parameter, "parameter").isIpAddressFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormatFalseIpV4()
	{
		String parameter = "1.256.3.4";
		Preconditions.requireThat(parameter, "parameter").isIpAddressFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormatFalseIpV6()
	{
		String parameter = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
		Preconditions.requireThat(parameter, "parameter").isIpAddressFormat();
	}

	@Test
	public void lengthInTrue()
	{
		String parameter = "123";
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat(parameter, "parameter").lengthIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthInFalse()
	{
		String parameter = "1234567";
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat(parameter, "parameter").lengthIn(range);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void lengthInNullParameter()
	{
		String parameter = null;
		Range<Integer> range = Range.closed(1, 5);
		Preconditions.requireThat(parameter, "parameter").lengthIn(range);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void lengthInNullRange()
	{
		String parameter = "1234567";
		Range<Integer> range = null;
		Preconditions.requireThat(parameter, "parameter").lengthIn(range);
	}

	@Test
	public void startsWithTrue()
	{
		String prefix = "home";
		String parameter = prefix + "1234";
		Preconditions.requireThat(parameter, "parameter").startsWith(prefix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWithFalse()
	{
		String prefix = "home";
		String parameter = "1234" + prefix;
		Preconditions.requireThat(parameter, "parameter").startsWith(prefix);
	}

	@Test
	public void doesNotStartWithTrue()
	{
		String prefix = "home";
		String parameter = "1234" + prefix;
		Preconditions.requireThat(parameter, "parameter").doesNotStartWith(prefix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWithFalse()
	{
		String prefix = "home";
		String parameter = prefix + "1234";
		Preconditions.requireThat(parameter, "parameter").doesNotStartWith(prefix);
	}

	@Test
	public void endsWithTrue()
	{
		String suffix = "home";
		String parameter = "1234" + suffix;
		Preconditions.requireThat(parameter, "parameter").endsWith(suffix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWithFalse()
	{
		String suffix = "home";
		String parameter = suffix + "1234";
		Preconditions.requireThat(parameter, "parameter").endsWith(suffix);
	}

	@Test
	public void doesNotEndWithTrue()
	{
		String suffix = "home";
		String parameter = suffix + "1234";
		Preconditions.requireThat(parameter, "parameter").doesNotEndWith(suffix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWithFalse()
	{
		String suffix = "home";
		String parameter = "1234" + suffix;
		Preconditions.requireThat(parameter, "parameter").doesNotEndWith(suffix);
	}
}
