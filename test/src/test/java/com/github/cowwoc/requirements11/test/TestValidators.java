package com.github.cowwoc.requirements11.test;

import com.github.cowwoc.requirements11.java.JavaRequireThat;
import com.github.cowwoc.requirements11.java.Validators;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.annotation.CheckReturnValue;
import com.github.cowwoc.requirements11.guava.GuavaAssertThat;
import com.github.cowwoc.requirements11.guava.GuavaCheckIf;
import com.github.cowwoc.requirements11.guava.GuavaRequireThat;
import com.github.cowwoc.requirements11.jackson.JacksonAssertThat;
import com.github.cowwoc.requirements11.jackson.JacksonCheckIf;
import com.github.cowwoc.requirements11.jackson.JacksonRequireThat;
import com.github.cowwoc.requirements11.java.JavaAssertThat;
import com.github.cowwoc.requirements11.java.JavaCheckIf;

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
		return new com.github.cowwoc.requirements11.test.TestValidatorsImpl(scope);
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