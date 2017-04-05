/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.impl;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;

/**
 * Default implementation of {@code GuavaVerifiers}.
 *
 * @author Gili Tzabari
 */
public final class GuavaVerifiersImpl implements GuavaVerifiers
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

	/**
	 * @param scope the application configuration
	 * @return scope
	 * @throws AssertionError if {@code scope} is null
	 */
	private static ApplicationScope verifyScope(ApplicationScope scope)
	{
		assert (scope != null): "scope may not be null";
		return scope;
	}
	private final ApplicationScope scope;
	private final Configuration config;

	/**
	 * Creates new verifiers.
	 */
	public GuavaVerifiersImpl()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Creates new verifiers with assertions disabled. This constructor is meant to be used by
	 * automated tests, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public GuavaVerifiersImpl(ApplicationScope scope)
	{
		this(verifyScope(scope), new Configuration());
	}

	/**
	 * Creates new verifiers.
	 *
	 * @param scope         the application configuration
	 * @param configuration the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public GuavaVerifiersImpl(ApplicationScope scope, Configuration configuration)
	{
		assert (scope != null): "scope may not be null";
		assert (configuration != null): "configuration may not be null";
		this.scope = scope;
		this.config = configuration;
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return config.assertionsAreEnabled();
	}

	@Override
	public GuavaVerifiers withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public GuavaVerifiers withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		verifyName(name);
		return new MultimapVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpMultimapVerifier<>(config);
	}

	@Override
	public GuavaVerifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public GuavaVerifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public GuavaVerifiers addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public GuavaVerifiers withConfiguration(Configuration configuration)
	{
		if (config.equals(configuration))
			return this;
		return new GuavaVerifiersImpl(scope, configuration);
	}
}
