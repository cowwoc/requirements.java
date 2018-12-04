/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.impl;

import org.bitbucket.cowwoc.requirements.java.Verifiers;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.java.Requirements.assertThat;

public final class VerifiersTest
{
	/**
	 * Regression test. Ensure that invoking addContext() on one verifier does not impact the context
	 * of a second verifier.
	 */
	@Test
	public void separateConfigurations()
	{
		Verifiers first = new Verifiers();
		first = first.addContext("name1", "value1");

		Verifiers second = new Verifiers();
		second = second.addContext("name2", "value2");

		assertThat("first.config", first.config).isNotEqualTo("second.config", second.config);
	}
}
