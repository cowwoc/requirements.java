/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.guava.impl.NoOpMultimapVerifier;

/**
 * Verifies a parameter if assertions are enabled; otherwise, does nothing.
 * <p>
 * Unlike {@link GuavaRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class GuavaAssertionVerifier implements Configurable<GuavaAssertionVerifier>
{
	private final ApplicationScope scope;
	private final boolean enabled;
	private final Configuration config;
	private final GuavaRequirementVerifier requirementVerifier;

	/**
	 * Creates a new assertion verifier.
	 * <p>
	 * To look up whether assertions are enabled for a particular class, invoke:
	 * <p>
	 * <code>
	 * boolean assertionsEnabled = false;<br>
	 * assert (assertionsEnabled = true);
	 * </code>
	 * <p>
	 * from within the class in question.
	 *
	 * @param enabled true if assertions are enabled for the class being verified
	 */
	public GuavaAssertionVerifier(boolean enabled)
	{
		this.scope = MainApplicationScope.INSTANCE;
		this.enabled = enabled;
		this.config = scope.getDefaultConfiguration();
		this.requirementVerifier = new GuavaRequirementVerifier(scope, config);
	}

	/**
	 * Creates a new assertion verifier.
	 * <p>
	 * To look up whether assertions are enabled for a particular class, invoke:
	 * <p>
	 * <code>
	 * boolean assertionsEnabled = false;<br>
	 * assert (assertionsEnabled = true);
	 * </code>
	 * <p>
	 * from within the class in question.
	 *
	 * @param scope   the application configuration
	 * @param enabled true if assertions are enabled for the class being verified
	 * @throws AssertionError if {@code scope} is null
	 */
	GuavaAssertionVerifier(ApplicationScope scope, boolean enabled)
	{
		this.scope = scope;
		this.enabled = enabled;
		this.config = scope.getDefaultConfiguration();
		this.requirementVerifier = new GuavaRequirementVerifier(scope, config);
	}

	/**
	 * Creates a new assertion verifier.
	 *
	 * @param enabled true if assertions are enabled for the class being verified
	 * @param config  the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	GuavaAssertionVerifier(ApplicationScope scope, boolean enabled, Configuration config)
	{
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.enabled = enabled;
		this.config = config;
		this.requirementVerifier = new GuavaRequirementVerifier(scope, config);
	}

	/**
	 * @return true if assertions are enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Sets the assertions state of the verifier.
	 *
	 * @param enabled true if assertions are enabled
	 * @return a verifier with the specified assertion state
	 */
	public GuavaAssertionVerifier withEnabled(boolean enabled)
	{
		if (enabled == this.enabled)
			return this;
		return new GuavaAssertionVerifier(scope, enabled, config);
	}

	/**
	 * Same as {@link GuavaRequirementVerifier#requireThat(Multimap, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpMultimapVerifier<>(config);
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public GuavaAssertionVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
