/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class InetAddressTest
{
	@Test
	public void isIpV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new CoreVerifiers(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV4_actualIsV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new CoreVerifiers(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test
	public void isIpV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new CoreVerifiers(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV6_actualIsV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new CoreVerifiers(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.2.3.4";
			new CoreVerifiers(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1";
			new CoreVerifiers(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.256.3.4";
			new CoreVerifiers(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
			new CoreVerifiers(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.2.3.4";
			InetAddress actualAsInetAddress = new CoreVerifiers(scope).
				requireThat(actual, "actual").asInetAddress().getActual();
			assert (actualAsInetAddress.toString().equals("/" + actual)):
				"actualAsInetAddress: " + actualAsInetAddress + ", actual: /" + actual;
		}
	}
}
