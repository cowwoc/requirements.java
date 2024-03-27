/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class InetAddressTest
{
	@Test
	public void isIpV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIpV4();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV4_actualIsV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIpV4();
		}
	}

	@Test
	public void isIpV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIpV6();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV6_actualIsV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIpV6();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.2.3.4";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.256.3.4";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			String actual = "1.2.3.4";
			InetAddress actualAsInetAddress = validators.requireThat(actual, "Actual").asInetAddress().
				getValue();
			validators.requireThat(actualAsInetAddress.toString(), "actualAsInetAddress").
				isEqualTo("/" + actual);
		}
	}

	@Test
	public void multipleFailuresNullIsIpv4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isIpV4().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsIpv6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isIpV6().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
