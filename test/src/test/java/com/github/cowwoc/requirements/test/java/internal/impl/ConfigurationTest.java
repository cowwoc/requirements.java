/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.impl;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;
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
	public void factorySeparateContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);

			TestValidatorsImpl factory2 = factory1.copy();
			factory2.withContext("factoryValue", "factoryName");

			String message1 = factory1.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetMessages().getFirst();
			String message2 = factory2.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetMessages().getFirst();

			requireThat(message1, "message1").contains("validatorName: \"validatorValue\"").
				doesNotContain("factoryName   : \"factoryValue\"");
			requireThat(message2, "message2").contains("validatorName: \"validatorValue\"").
				contains("factoryName  : \"factoryValue\"");
		}
	}

	@Test
	public void factoryInheritedContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);
			factory1.withContext("factoryValue", "factoryName");

			TestValidatorsImpl factory2 = factory1.copy();
			String message = factory2.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetMessages().getFirst();

			requireThat(message, "message2").contains("validatorName: \"validatorValue\"").
				contains("factoryName  : \"factoryValue\"");
		}
	}

	/**
	 * Ensure that the validator context is separate from the factory context.
	 */
	@Test
	public void validatorContextSeparateFromFactory()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl factory = new TestValidatorsImpl(scope);

			String message = factory.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetMessages().getFirst();

			// Ensure that this does not affect pre-existing validators
			factory.withContext("factoryValue", "factoryName");

			requireThat(message, "message2").contains("validatorName: \"validatorValue\"").
				doesNotContain("factoryName  : \"factoryValue\"");
		}
	}

	@Test
	public void validatorOverridesFactoryContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);
			factory1.withContext("factoryValue", "collision");

			TestValidatorsImpl factory2 = factory1.copy();

			String message1 = factory1.checkIf("value", "name").
				withContext("validatorValue", "collision").isNull().elseGetMessages().getFirst();
			String message2 = factory2.checkIf("value", "name").
				withContext("validatorValue", "collision").isNull().elseGetMessages().getFirst();

			requireThat(message1, "message1").contains("collision: \"validatorValue\"").
				doesNotContain("collision: \"factoryValue\"");
			requireThat(message2, "message2").contains("collision: \"validatorValue\"").
				doesNotContain("collision: \"factoryValue\"");
		}
	}

	@Test
	public void exceptionOverridesValidatorContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			validators.withContext("factoryValue", "name2");

			String message = validators.checkIf("value", "name").
				withContext("validatorValue", "name2").isNull().elseGetMessages().getFirst();
			validators.requireThat(message, "message").contains("Actual: \"value\"").
				doesNotContain("name2: \"validatorValue\"").
				doesNotContain("name2: \"factoryValue\"");
		}
	}
}