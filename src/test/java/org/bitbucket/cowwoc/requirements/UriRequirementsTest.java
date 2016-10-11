/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class UriRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		URI actual = URI.create("http://host.com/");
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		URI actual = URI.create("http://host.com/");
		Requirements.requireThat(actual, "");
	}

	@Test
	public void isAbsolute()
	{
		URI actual = URI.create("http://host.com/index.html");
		Requirements.requireThat(actual, "actual").isAbsolute();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		URI actual = URI.create("../index.html");
		Requirements.requireThat(actual, "actual").isAbsolute();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		URI actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
