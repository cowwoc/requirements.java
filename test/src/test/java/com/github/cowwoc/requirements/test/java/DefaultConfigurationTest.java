/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class DefaultConfigurationTest
{
	// Can't test TerminalEncoding method because it requires access to a physical terminal that is outside
	// our control. Testing using a stubbed terminal wouldn't be meaningful.

	@Test
	public void cleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			Configuration configuration = validators.configuration();
			validators.requireThat(configuration.cleanStackTrace(), "configuration.cleanStackTrace()").isTrue();
		}
	}

	@Test
	public void cleanStackTraceFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(validators.configuration().cleanStackTrace(), "configuration.cleanStackTrace()"
			).isTrue();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(false);
			}
			validators.requireThat(validators.configuration().cleanStackTrace(), "configuration.cleanStackTrace()"
			).isFalse();
		}
	}

	@Test
	public void cleanStackTraceTrue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(false);
			}
			validators.requireThat(validators.configuration().cleanStackTrace(), "configuration.cleanStackTrace()"
			).isFalse();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.cleanStackTrace(true);
			}
			validators.requireThat(validators.configuration().cleanStackTrace(), "configuration.cleanStackTrace()"
			).isTrue();
		}
	}

	@Test
	public void isDiffEnabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()").
				isTrue();
		}
	}

	@Test
	public void withoutDiff()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()"
			).isTrue();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.includeDiff(false);
			}
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()"
			).isFalse();
		}
	}

	@Test
	public void withDiff()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()"
			).isTrue();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.includeDiff(false);
			}
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()"
			).isFalse();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.includeDiff(true);
			}
			validators.requireThat(validators.configuration().includeDiff(), "configuration.diffEnabled()"
			).isTrue();
		}
	}
}