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
import org.bitbucket.cowwoc.requirements.guava.impl.MultimapVerifierImpl;

/**
 * Verifies a parameter.
 * <p>
 * Unlike {@link GuavaRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class GuavaRequirementVerifier implements Configurable<GuavaRequirementVerifier>
{
	/**
	 * @param name the name of the actual value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private static void verifyName(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
	}
	private final ApplicationScope scope;
	private final Configuration config;

	/**
	 * Creates a new requirement verifier.
	 */
	public GuavaRequirementVerifier()
	{
		this.scope = MainApplicationScope.INSTANCE;
		this.config = scope.getDefaultConfiguration();
	}

	/**
	 * Creates a new requirement verifier. This constructor is meant to be used by automated tests,
	 * not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	GuavaRequirementVerifier(ApplicationScope scope)
	{
		assert (scope != null): "scope may not be null";
		this.scope = scope;
		this.config = scope.getDefaultConfiguration();
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	GuavaRequirementVerifier(ApplicationScope scope, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		verifyName(name);
		return new MultimapVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public GuavaRequirementVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
