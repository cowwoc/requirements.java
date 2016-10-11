/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class StringRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		String actual = "value";
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		String actual = "value";
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isEmpty()
	{
		String actual = "";
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		String actual = "   ";
		Requirements.requireThat(actual, "actual").isEmpty();
	}

	@Test
	public void trimIsEmpty()
	{
		String actual = "   ";
		Requirements.requireThat(actual, "actual").trim().isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		String actual = "value";
		Requirements.requireThat(actual, "actual").trim().isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		String actual = "   ";
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		String actual = "";
		Requirements.requireThat(actual, "actual").isNotEmpty();
	}

	@Test
	public void trimIsNotEmpty()
	{
		String actual = "value";
		Requirements.requireThat(actual, "actual").trim().isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		String actual = "   ";
		Requirements.requireThat(actual, "actual").trim().isNotEmpty();
	}

	@Test
	public void isEmailFormat()
	{
		String actual = "name@gmail.com";
		Requirements.requireThat(actual, "actual").isEmailFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmailFormat_False()
	{
		String actual = "name.com";
		Requirements.requireThat(actual, "actual").isEmailFormat();
	}

	@Test
	public void isIpAddressFormat_actualIsIpV4()
	{
		String actual = "1.2.3.4";
		Requirements.requireThat(actual, "actual").isIpAddressFormat();
	}

	@Test
	public void isIpAddressFormat_actualIsIpV6()
	{
		String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1";
		Requirements.requireThat(actual, "actual").isIpAddressFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormat_actualIsInvalidIpV4()
	{
		String actual = "1.256.3.4";
		Requirements.requireThat(actual, "actual").isIpAddressFormat();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormat_actualIsInvalidIpV6()
	{
		String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
		Requirements.requireThat(actual, "actual").isIpAddressFormat();
	}

	@Test
	public void startsWith()
	{
		String prefix = "home";
		String actual = prefix + "1234";
		Requirements.requireThat(actual, "actual").startsWith(prefix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		String prefix = "home";
		String actual = "1234" + prefix;
		Requirements.requireThat(actual, "actual").startsWith(prefix);
	}

	@Test
	public void doesNotStartWith()
	{
		String prefix = "home";
		String actual = "1234" + prefix;
		Requirements.requireThat(actual, "actual").doesNotStartWith(prefix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		String prefix = "home";
		String actual = prefix + "1234";
		Requirements.requireThat(actual, "actual").doesNotStartWith(prefix);
	}

	@Test
	public void endsWith()
	{
		String suffix = "home";
		String actual = "1234" + suffix;
		Requirements.requireThat(actual, "actual").endsWith(suffix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		String suffix = "home";
		String actual = suffix + "1234";
		Requirements.requireThat(actual, "actual").endsWith(suffix);
	}

	@Test
	public void doesNotEndWith()
	{
		String suffix = "home";
		String actual = suffix + "1234";
		Requirements.requireThat(actual, "actual").doesNotEndWith(suffix);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		String suffix = "home";
		String actual = "1234" + suffix;
		Requirements.requireThat(actual, "actual").doesNotEndWith(suffix);
	}

	@Test
	public void lengthIsEqualTo()
	{
		String actual = "value";
		Requirements.requireThat(actual, "actual").length().isEqualTo(actual.length());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		String actual = "1234567890";
		Requirements.requireThat(actual, "actual").length().isEqualTo(actual.length() + 1);
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		String actual = "value";
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(actual.length() + 1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		String actual = "1234567890";
		Requirements.requireThat(actual, "actual").length().isNotEqualTo(actual.length());
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		String actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
