/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class DefaultConfigurationTest
{
	// Can't test TerminalEncoding method because it requires access to a physical terminal that is outside
	// our control. Testing using a stubbed terminal wouldn't be meaningful.

	@Test
	public void cleanStackTrace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Configuration configuration = validators.configuration();
			validators.requireThat(configuration.cleanStackTrace(), "configuration.cleanStackTrace()").isTrue();
		}
	}

	@Test
	public void cleanStackTraceFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

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
			TestValidators validators = TestValidators.of(scope);

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
	public void allowDiff()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()").
				isTrue();
		}
	}

	@Test
	public void disallowDiff()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()"
			).isTrue();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.allowDiff(false);
			}
			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()"
			).isFalse();
		}
	}

	@Test
	public void withDiff()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);
			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()"
			).isTrue();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.allowDiff(false);
			}
			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()"
			).isFalse();
			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.allowDiff(true);
			}
			validators.requireThat(validators.configuration().allowDiff(), "configuration.allowDiff()"
			).isTrue();
		}
	}
}