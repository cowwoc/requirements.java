/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import org.testng.annotations.Test;

public final class GlobalConfigurationTest
{
	// Can't test TerminalEncoding, TerminalWidth methods because they require access to a physical terminal
	// that is outside our control. Testing using a stubbed terminal wouldn't be meaningful.

	@Test
	public void isCleanStackTrace()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration();
			new Requirements(scope).requireThat(globalConfiguration.isCleanStackTrace(),
				"globalConfiguration.isCleanStackTrace()").isTrue();
		}
	}

	@Test
	public void withoutCleanStackTrace()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration();
			Requirements requirements = new Requirements(scope);
			requirements.requireThat(globalConfiguration.isCleanStackTrace(),
				"globalConfiguration.isCleanStackTrace()").isTrue();
			globalConfiguration.withoutCleanStackTrace();
			requirements.requireThat(globalConfiguration.isCleanStackTrace(),
				"globalConfiguration.isCleanStackTrace()").isFalse();
		}
	}

	@Test
	public void withCleanStackTrace()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration();
			Requirements requirements = new Requirements(scope);
			globalConfiguration.withoutCleanStackTrace();
			requirements.requireThat(globalConfiguration.isCleanStackTrace(),
				"globalConfiguration.isCleanStackTrace()").isFalse();
			globalConfiguration.withCleanStackTrace();
			requirements.requireThat(globalConfiguration.isCleanStackTrace(),
				"globalConfiguration.isCleanStackTrace()").isTrue();
		}
	}

	@Test
	public void isDiffEnabled()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration().withoutCleanStackTrace();
			new Requirements(scope).requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isTrue();
		}
	}

	@Test
	public void withoutDiff()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Requirements requirements = new Requirements(scope);
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration().withoutCleanStackTrace();
			requirements.requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isTrue();
			globalConfiguration.withoutDiff();
			requirements.requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isFalse();
		}
	}

	@Test
	public void withDiff()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Requirements requirements = new Requirements(scope);
			GlobalConfiguration globalConfiguration = scope.getGlobalConfiguration().withoutCleanStackTrace();
			requirements.requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isTrue();
			globalConfiguration.withoutDiff();
			requirements.requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isFalse();
			globalConfiguration.withDiff();
			requirements.requireThat(globalConfiguration.isDiffEnabled(),
				"globalConfiguration.isDiffEnabled()").isTrue();
		}
	}
}