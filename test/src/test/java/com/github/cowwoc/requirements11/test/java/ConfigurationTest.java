/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.test.java;

import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.test.TestValidators;
import com.github.cowwoc.requirements11.test.TestValidatorsImpl;
import com.github.cowwoc.requirements11.test.scope.TestApplicationScope;
import com.google.common.collect.Sets;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.cowwoc.requirements11.java.TerminalEncoding.NONE;

public final class ConfigurationTest
{
	@Test
	public void withStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			try
			{
				Set<Integer> actual = Sets.newLinkedHashSetWithExpectedSize(2);
				actual.add(1);
				actual.add(2);

				Set<Integer> notEqual = Set.of();
				try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
				{
					configurationUpdater.stringMappers().put(LinkedHashSet.class, (value, seen) ->
					{
						@SuppressWarnings("unchecked")
						List<Integer> result = new ArrayList<>((LinkedHashSet<Integer>) value);
						result.sort(Comparator.reverseOrder());
						return result.toString();
					});
				}
				validators.requireThat(actual, "actual").isEqualTo(notEqual);
			}
			catch (IllegalArgumentException e)
			{
				validators.requireThat(e.getMessage(), "e.getMessage()").contains("[2, 1]");
			}
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void exceptionTransformer()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
			{
				configurationUpdater.exceptionTransformer(IllegalStateException::new);
			}
			validators.requireThat(false, "false").isEqualTo(true);
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void fluentApi()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope).updateConfiguration(c ->
				c.exceptionTransformer(IllegalStateException::new));

			validators.requireThat(false, "false").isEqualTo(true);
		}
	}

	/**
	 * Ensure that modifying one instance of DefaultConfiguration does not modify the other.
	 */
	@Test
	public void separateConfigurations()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.allowDiff(true);
			}
			Configuration first = validators.configuration();
			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.allowDiff(false);
			}
			Configuration second = validators.configuration();

			validators.requireThat(first.allowDiff(), "first.config").
				isNotEqualTo(second.allowDiff(), "second.config");
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
			TestValidators validators = TestValidators.of(scope);

			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.allowDiff(true);
			}
			Configuration first = validators.configuration();

			try (ConfigurationUpdater configuration = validators.updateConfiguration())
			{
				configuration.allowDiff(false);
			}
			Configuration second = validators.configuration();

			validators.requireThat(first.allowDiff(), "first.config").
				isNotEqualTo(second.allowDiff(), "second.config");
		}
	}

	@Test
	public void factorySeparateContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);
			TestValidators factory2 = factory1.copy();
			factory2.withContext("factoryValue", "factoryName");

			String message1 = factory1.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetFailures().getMessages().getFirst();
			String message2 = factory2.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetFailures().getMessages().getFirst();

			validators.requireThat(message1, "message1").contains("validatorName: \"validatorValue\"").
				doesNotContain("factoryName   : \"factoryValue\"");
			validators.requireThat(message2, "message2").contains("validatorName: \"validatorValue\"").
				contains("factoryName  : \"factoryValue\"");
		}
	}

	@Test
	public void factoryInheritedContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);
			factory1.withContext("factoryValue", "factoryName");

			TestValidators factory2 = factory1.copy();
			String message = factory2.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetFailures().getMessages().getFirst();

			validators.requireThat(message, "message2").contains("validatorName: \"validatorValue\"").
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
			TestValidators validators = TestValidators.of(scope);

			TestValidatorsImpl factory = new TestValidatorsImpl(scope);
			String message = factory.checkIf("value", "name").
				withContext("validatorValue", "validatorName").isNull().elseGetFailures().getMessages().getFirst();

			// Ensure that this does not affect pre-existing validators
			factory.withContext("factoryValue", "factoryName");

			validators.requireThat(message, "message2").contains("validatorName: \"validatorValue\"").
				doesNotContain("factoryName  : \"factoryValue\"");
		}
	}

	/**
	 * Ensure that a validator can override the context set by its factory.
	 */
	@Test
	public void validatorOverridesFactoryContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			TestValidatorsImpl factory1 = new TestValidatorsImpl(scope);
			factory1.withContext("factoryValue", "collision");

			TestValidators factory2 = factory1.copy();
			String message1 = factory1.checkIf("value", "name").
				withContext("validatorValue", "collision").isNull().elseGetFailures().getMessages().getFirst();
			String message2 = factory2.checkIf("value", "name").
				withContext("validatorValue", "collision").isNull().elseGetFailures().getMessages().getFirst();

			validators.requireThat(message1, "message1").contains("collision: \"validatorValue\"").
				doesNotContain("collision: \"factoryValue\"");
			validators.requireThat(message2, "message2").contains("collision: \"validatorValue\"").
				doesNotContain("collision: \"factoryValue\"");
		}
	}

	/**
	 * Ensure that an exception can override the context set by the validator.
	 */
	@Test
	public void exceptionOverridesValidatorContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);
			validators.withContext("factoryValue", "missing");

			Set<Integer> actual = Set.of(1, 2, 3);
			Set<Integer> expected = Set.of(1, 2, 3, 4);

			String message = validators.checkIf(actual, "actual").
				withContext("validatorValue", "missing").containsAll(expected, "expected").
				elseGetFailures().getMessages().getFirst();
			validators.requireThat(message, "message").contains("missing : [4]").
				doesNotContain("missing: \"validatorValue\"").
				doesNotContain("missing: \"factoryValue\"");
		}
	}
}