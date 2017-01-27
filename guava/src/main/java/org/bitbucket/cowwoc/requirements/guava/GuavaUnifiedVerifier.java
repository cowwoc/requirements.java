/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ext.Configurable;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Combines the functionality of {@link GuavaRequirementVerifier} and {@link GuavaAssertionVerifier}
 * into a single class.
 * <p>
 * Unlike {@link GuavaRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class GuavaUnifiedVerifier implements Configurable<GuavaUnifiedVerifier>
{
	private final Configuration config;
	private final GuavaAssertionVerifier assertions;
	private final GuavaRequirementVerifier requirements;

	/**
	 * @param scope             the application configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 * @throws AssertionError if any of the arguments are null
	 */
	GuavaUnifiedVerifier(ApplicationScope scope, Configuration config, boolean assertionsEnabled)
	{
		this.config = config;
		this.assertions = new GuavaAssertionVerifier(scope, assertionsEnabled, config);
		this.requirements = new GuavaRequirementVerifier(scope, config);
	}

	/**
	 * Equivalent to {@link #GuavaUnifiedVerifier(ApplicationScope, Configuration, boolean) GuavaUnifiedVerifier(scope, scope.getDefaultConfiguration(), assertionEnabled).
	 *
	 * @param scope             the application configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 * @throws AssertionError if any of the arguments are null
	 */
	GuavaUnifiedVerifier(ApplicationScope scope, boolean assertionsEnabled)
	{
		this(scope, scope.getDefaultConfiguration(), assertionsEnabled);
	}

	/**
	 * @return true if {@code assertThat()} carries out a verification
	 */
	public boolean assertionsAreEnabled()
	{
		return assertions.isEnabled();
	}

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Multimap, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public GuavaUnifiedVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
