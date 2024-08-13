/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class InetAddressTest
{
	@Test
	public void isIpV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV4_actualIsV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test
	public void isIpV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV6_actualIsV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test
	public void multipleFailuresNullIsIpv4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an IP v4 address",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isIpV4().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsIpv6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an IP v6 address",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isIpV6().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
