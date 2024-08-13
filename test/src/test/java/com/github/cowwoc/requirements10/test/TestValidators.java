package com.github.cowwoc.requirements10.test;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.guava.GuavaAssertThat;
import com.github.cowwoc.requirements10.guava.GuavaCheckIf;
import com.github.cowwoc.requirements10.guava.GuavaRequireThat;
import com.github.cowwoc.requirements10.jackson.JacksonAssertThat;
import com.github.cowwoc.requirements10.jackson.JacksonCheckIf;
import com.github.cowwoc.requirements10.jackson.JacksonRequireThat;
import com.github.cowwoc.requirements10.java.JavaAssertThat;
import com.github.cowwoc.requirements10.java.JavaCheckIf;
import com.github.cowwoc.requirements10.java.JavaRequireThat;
import com.github.cowwoc.requirements10.java.Validators;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;

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