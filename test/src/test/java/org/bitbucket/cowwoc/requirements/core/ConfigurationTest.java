/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import static org.bitbucket.cowwoc.requirements.core.Requirements.requireThat;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ConfigurationTest
{
	@Test
	public void contextWithCustomToString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String value = "1+1=";
			new Verifiers(scope).addContext("key", () -> value + "2").
				requireThat("actual", "actual").isEqualTo("expected");
		}
		catch (IllegalArgumentException e)
		{
			requireThat(e.getMessage(), "e.getMessage()").contains("1+1=2");
		}
	}
}
