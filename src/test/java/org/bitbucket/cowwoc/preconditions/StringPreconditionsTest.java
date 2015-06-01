/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

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
	public void isEmptyTrue()
	{
		String parameter = "";
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		String parameter = "   ";
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isTrimEmptyTrue()
	{
		String parameter = "   ";
		Preconditions.requireThat(parameter, "parameter").trim().isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimEmptyFalse()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").trim().isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		String parameter = "   ";
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		String parameter = "";
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void isTrimNotEmptyTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").trim().isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimNotEmptyFalse()
	{
		String parameter = "   ";
		Preconditions.requireThat(parameter, "parameter").trim().isNotEmpty();
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

	@Test
	public void lengthIsEqualToTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").length().isEqualTo(parameter.length());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualToFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat(parameter, "parameter").length().isEqualTo(parameter.length() + 1);
	}

	@Test
	public void lengthIsNotEqualToTrue()
	{
		String parameter = "value";
		Preconditions.requireThat(parameter, "parameter").length().isNotEqualTo(parameter.length() + 1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualToFalse()
	{
		String parameter = "1234567890";
		Preconditions.requireThat(parameter, "parameter").length().isNotEqualTo(parameter.length());
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		String parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
