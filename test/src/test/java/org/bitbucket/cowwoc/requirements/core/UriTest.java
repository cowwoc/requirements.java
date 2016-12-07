/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class UriTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			URI actual = URI.create("http://host.com/");
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			URI actual = URI.create("http://host.com/");
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isAbsolute()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			URI actual = URI.create("http://host.com/index.html");
			new RequirementVerifier(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			URI actual = URI.create("../index.html");
			new RequirementVerifier(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			URI actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
