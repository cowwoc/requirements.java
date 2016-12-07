/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.annotations.Beta;
import org.bitbucket.cowwoc.requirements.core.scope.MainSingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.spi.Configuration;
import org.bitbucket.cowwoc.requirements.core.spi.Verifier;

/**
 * Verifies a parameter.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class RequirementVerifier implements Verifier
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
	private final SingletonScope scope;
	private final Configuration config;

	/**
	 * Creates a new requirement verifier.
	 */
	public RequirementVerifier()
	{
		this.scope = MainSingletonScope.INSTANCE;
		this.config = Configuration.initial();
	}

	/**
	 * Creates a new requirement verifier. This constructor is meant to be used by automated tests,
	 * not by users.
	 *
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	RequirementVerifier(SingletonScope scope)
	{
		assert (scope != null): "scope may not be null";
		this.scope = scope;
		this.config = Configuration.initial();
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param scope  the system configuration
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	RequirementVerifier(SingletonScope scope, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	@Override
	public RequirementVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(scope, newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new RequirementVerifier(scope, newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(scope, newConfig);
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
}
