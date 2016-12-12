/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class EmailTest
{
	@Test
	public void asEmailAddress()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "name@host.com";
			new RequirementVerifier(scope).requireThat(actual, "actual").asEmailAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void asEmailAddress_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "host.com";
			new RequirementVerifier(scope).requireThat(actual, "actual").asEmailAddress();
		}
	}
}
