/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class InetAddressTest
{
	@Test
	public void isIpV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new Requirements(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV4_actualIsV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new Requirements(scope).requireThat(actual, "actual").isIpV4();
		}
	}

	@Test
	public void isIpV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			new Requirements(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV6_actualIsV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = InetAddress.getByName("1.2.3.4");
			new Requirements(scope).requireThat(actual, "actual").isIpV6();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.2.3.4";
			new Requirements(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test
	public void asIpAddress_actualIsIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1";
			new Requirements(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.256.3.4";
			new Requirements(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asIpAddress_actualIsInvalidIpV6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
			new Requirements(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1.2.3.4";
			InetAddress actualAsInetAddress = new Requirements(scope).
				requireThat(actual, "actual").asInetAddress().getActual();
			assert (actualAsInetAddress.toString().equals("/" + actual)) :
				"actualAsInetAddress: " + actualAsInetAddress + ", actual: /" + actual;
		}
	}

	@Test
	public void validateThatNullIsIpv4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isIpV4().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsIpv6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isIpV6().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
