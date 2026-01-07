/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

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