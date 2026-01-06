/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.TestValidatorsImpl;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
public final class InetAddressTest
{
	@Test
	public void isIpV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = InetAddress.getByName("1.2.3.4");
			validators.requireThat(actual, "actual").isIpV4();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV4_actualIsV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			validators.requireThat(actual, "actual").isIpV4();
		}
	}

	@Test
	public void isIpV6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = InetAddress.getByName("2001:db8:a0b:12f0::1");
			validators.requireThat(actual, "actual").isIpV6();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpV6_actualIsV4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = InetAddress.getByName("1.2.3.4");
			validators.requireThat(actual, "actual").isIpV6();
		}
	}

	@Test
	public void multipleFailuresNullIsIpv4()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an IP v4 address", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isIpV4().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsIpv6()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			InetAddress actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an IP v6 address", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isIpV6().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
