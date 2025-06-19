/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test;

import io.github.cowwoc.requirements12.jackson.JacksonAssertThat;
import io.github.cowwoc.requirements12.jackson.JacksonCheckIf;
import io.github.cowwoc.requirements12.jackson.JacksonRequireThat;
import io.github.cowwoc.requirements12.java.JavaAssertThat;
import io.github.cowwoc.requirements12.java.JavaCheckIf;
import io.github.cowwoc.requirements12.java.JavaRequireThat;
import io.github.cowwoc.requirements12.java.Validators;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.annotation.CheckReturnValue;
import io.github.cowwoc.requirements12.guava.GuavaAssertThat;
import io.github.cowwoc.requirements12.guava.GuavaCheckIf;
import io.github.cowwoc.requirements12.guava.GuavaRequireThat;

import java.util.function.Consumer;

/**
 * Validators for automated tests.
 */
public interface TestValidators
	extends Validators<TestValidators>,
	JavaRequireThat, JavaAssertThat, JavaCheckIf,
	GuavaRequireThat, GuavaAssertThat, GuavaCheckIf,
	JacksonRequireThat, JacksonAssertThat, JacksonCheckIf
{
	/**
	 * Creates a validator factory with a custom configuration.
	 *
	 * @param scope the application configuration
	 * @return an instance of this interface
	 * @throws NullPointerException if any of the arguments are null
	 */
	static TestValidators of(ApplicationScope scope)
	{
		return new TestValidatorsImpl(scope);
	}

	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	Configuration configuration();

	/**
	 * Updates the configuration that will be used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	ConfigurationUpdater updateConfiguration();

	/**
	 * Updates the configuration that will be used by new validators, using a fluent API that automatically
	 * applies the changes on exit. For example:
	 * {@snippet :
	 * validators.apply(v -> v.updateConfiguration().allowDiff(false)).
	 * requireThat(value, name);
	 *}
	 *
	 * @param consumer consumes the configuration updater
	 * @return this
	 * @throws NullPointerException if {@code updater} is null
	 */
	TestValidators updateConfiguration(Consumer<ConfigurationUpdater> consumer);
}