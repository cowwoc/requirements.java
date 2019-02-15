/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl.test;

import org.bitbucket.cowwoc.requirements.DefaultRequirements;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.assertThat;

public final class ConfigurationTest
{
	/**
	 * Regression test. Ensure that invoking addContext() on one verifier does not impact the context
	 * of a second verifier.
	 */
	@Test
	public void separateConfigurations()
	{
		Configuration first = new Configuration();
		first = first.putContext("name1", "value1");

		Configuration second = new Configuration();
		second = second.putContext("name2", "value2");

		assertThat(first, "first.config").isNotEqualTo(second, "second.config");
	}

	@Test
	public void threadConfiguration()
	{
		DefaultRequirements.getThreadConfiguration().fixme;
	}
}
