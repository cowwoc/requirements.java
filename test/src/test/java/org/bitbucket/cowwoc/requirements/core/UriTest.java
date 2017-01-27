/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class UriTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new CoreRequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new CoreRequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/index.html");
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("../index.html");
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "../index.html";
			URI actualAsUri = new CoreRequirementVerifier(scope).requireThat(actual, "actual").asUri().
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
			URI actual = null;
			new CoreAssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
