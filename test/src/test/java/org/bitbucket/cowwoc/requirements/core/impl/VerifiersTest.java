/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import static org.bitbucket.cowwoc.requirements.core.Requirements.assertThat;
import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class VerifiersTest
{
	/**
	 * Ensure that verifiers instantiate separate configuration instances.
	 */
	@Test
	public void separateConfigurations()
	{
		Verifiers first = new Verifiers();
		first.addContext("key1", "value1");

		Verifiers second = new Verifiers();
		second.addContext("key2", "value2");

		assertThat(first.config, "first.config").isNotEqualTo(second.config, "second.config");
	}
}