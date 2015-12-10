/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class UriPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		URI parameter = URI.create("http://host.com/");
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		URI parameter = URI.create("http://host.com/");
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isAbsoluteTrue()
	{
		URI parameter = URI.create("http://host.com/index.html");
		Preconditions.requireThat(parameter, "parameter").isAbsolute();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsoluteFalse()
	{
		URI parameter = URI.create("../index.html");
		Preconditions.requireThat(parameter, "parameter").isAbsolute();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		URI parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
