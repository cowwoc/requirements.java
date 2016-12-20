/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;

/**
 * An abstract class used to hide test methods from end-users.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
abstract class AbstractUnifiedVerifier
{
	private final SingletonScope scope;
	private final Configuration config;
	private final AssertionVerifier assertions;
	private final RequirementVerifier requirements;

	/**
	 * @param scope             the system configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 * @throws AssertionError if any of the arguments are null
	 */
	AbstractUnifiedVerifier(SingletonScope scope, Configuration config, boolean assertionsEnabled)
	{
		this.scope = scope;
		this.config = config;
		this.assertions = new AssertionVerifier(scope, assertionsEnabled, config);
		this.requirements = new RequirementVerifier(scope, config);
	}

	/**
	 * @param scope   the system configuration
	 * @param enabled true if assertions are enabled for the class being verified
	 * @param config  the instance configuration
	 * @return a new assertion verifier
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	protected abstract AbstractUnifiedVerifier newInstance(SingletonScope scope,
		Configuration config, boolean enabled);

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

	protected AbstractUnifiedVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return newInstance(scope, newConfig, assertions.isEnabled());
	}

	protected AbstractUnifiedVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return newInstance(scope, newConfig, assertions.isEnabled());
	}

	protected AbstractUnifiedVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return newInstance(scope, newConfig, assertions.isEnabled());
	}
}
