/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class UriTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/index.html");
			new Requirements(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("../index.html");
			new Requirements(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "../index.html";
			URI actualAsUri = new Requirements(scope).requireThat(actual, "actual").asUri().
				getActual();
			assert (actualAsUri.toString().equals(actual)) : "actualAsUri: " + actualAsUri + ", actual: " +
				actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new Requirements(scope).requireThat(actual, "actual").asUri().asUrl().
				getActual();
			assert (actualAsUrl.toString().equals(actual)) : "actualAsUri: " + actualAsUrl + ", actual: " +
				actual;
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			URI actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
