/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class UrlTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new Requirements(scope).requireThat(actual, "actual").asUrl().getActual();
			assert (actualAsUrl.toString().equals(actual)) : "actualAsUrl: " + actualAsUrl + ", actual: " + actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URI actualAsUri = new Requirements(scope).requireThat(actual, "actual").asUrl().asUri().getActual();
			assert (actualAsUri.toString().equals(actual)) : "actualAsUri: " + actualAsUri + ", actual: " + actual;
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			URL actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
