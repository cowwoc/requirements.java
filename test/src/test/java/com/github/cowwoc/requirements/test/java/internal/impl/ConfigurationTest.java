/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.impl;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.ScopedContext;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class ConfigurationTest
{
	/**
	 * Ensure that modifying one instance of DefaultConfiguration does not modify the other.
	 */
	@Test
	public void separateConfigurations()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.includeDiff(true);
			}
			Configuration first = validators.configuration();
			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.includeDiff(false);
			}
			Configuration second = validators.configuration();

			validators.requireThat(first.includeDiff(), "first.config").
				isNotEqualTo(second.includeDiff(), "second.config");
		}
	}

	/**
	 * Ensure that modifying the default configuration does not modify existing configuration instances.
	 */
	@Test
	public void inheritDefaultConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.includeDiff(true);
			}
			Configuration first = validators.configuration();

			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.includeDiff(false);
			}
			Configuration second = validators.configuration();

			validators.requireThat(first.includeDiff(), "first.config").
				isNotEqualTo(second.includeDiff(), "second.config");
		}
	}

	@Test
	public void threadConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ScopedContext context = validators.threadContext())
			{
				context.put("threadValue", "threadName");
				String message = validators.checkIf("value", "name").
					putContext("validatorValue", "validatorName").isNull().elseGetMessages().getFirst();
				validators.requireThat(message, "message").contains("validatorName: \"validatorValue\"").
					contains("threadName   : \"threadValue\"");
			}
		}
	}

	@Test
	public void validatorOverridesThreadContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ScopedContext context = validators.threadContext())
			{
				context.put("threadValue", "collision");
				String message = validators.checkIf("value", "name").
					putContext("validatorValue", "collision").isNull().elseGetMessages().getFirst();
				validators.requireThat(message, "message").contains("collision: \"validatorValue\"").
					doesNotContain("collision: \"threadValue\"");
			}
		}
	}

	@Test
	public void exceptionOverridesValidatorContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			try (ScopedContext context = validators.threadContext())
			{
				context.put("threadValue", "name2");
				String message = validators.checkIf("value", "name").
					putContext("validatorValue", "name2").isNull().elseGetMessages().getFirst();
				validators.requireThat(message, "message").contains("Actual: \"value\"").
					doesNotContain("name2: \"validatorValue\"").
					doesNotContain("name2: \"threadValue\"");
			}
		}
	}
}