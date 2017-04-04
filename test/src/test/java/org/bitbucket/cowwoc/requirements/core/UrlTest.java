/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class UrlTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Verifiers(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Verifiers(scope).requireThat(actual, "");
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new Verifiers(scope).requireThat(actual, "actual").asUrl().
				getActual();
			assert (actualAsUrl.toString().equals(actual)): "actualAsUrl: " + actualAsUrl + ", actual: " +
				actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URI actualAsUri = new Verifiers(scope).requireThat(actual, "actual").asUrl().asUri().
				getActual();
			assert (actualAsUri.toString().equals(actual)): "actualAsUri: " + actualAsUri + ", actual: " +
				actual;
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			URL actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
