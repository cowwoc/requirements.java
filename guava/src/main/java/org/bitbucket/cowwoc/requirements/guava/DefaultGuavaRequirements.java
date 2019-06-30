/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.internal.MultimapValidatorImpl;
import org.bitbucket.cowwoc.requirements.guava.internal.MultimapVerifierImpl;
import org.bitbucket.cowwoc.requirements.guava.internal.NoOpMultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Verifiers;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

/**
 * Default implementation of GuavaRequirements.
 */
public final class DefaultGuavaRequirements implements GuavaRequirements
{
	/**
	 * @param scope the application configuration
	 * @return scope
	 * @throws AssertionError if {@code scope} is null
	 */
	private static ApplicationScope verifyScope(ApplicationScope scope)
	{
		assert (scope != null) : "scope may not be null";
		return scope;
	}

	private final ApplicationScope scope;
	private Configuration config;

	public DefaultGuavaRequirements()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Equivalent to
	 * {@link #DefaultGuavaRequirements(ApplicationScope, Configuration)
	 * DefaultJavaRequirements(scope, scope.getGlobalConfiguration())}.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public DefaultGuavaRequirements(ApplicationScope scope)
	{
		this(verifyScope(scope), scope.getDefaultConfiguration().get());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultGuavaRequirements(ApplicationScope scope, Configuration configuration)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "configuration may not be null";
		this.scope = scope;
		this.config = configuration;
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return config.assertionsAreEnabled();
	}

	@Override
	public GuavaRequirements withAssertionsEnabled()
	{
		config.withAssertionsEnabled();
		return this;
	}

	@Override
	public GuavaRequirements withAssertionsDisabled()
	{
		config.withAssertionsDisabled();
		return this;
	}

	@Override
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MultimapVerifierImpl<>(scope, name, actual, config);
	}

	@Override
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpMultimapVerifier<>(config);
	}

	@Override
	public <K, V> MultimapValidator<K, V> validateThat(Multimap<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MultimapValidatorImpl<>(scope, name, actual, config, new ArrayList<>());
	}

	@Override
	public boolean isDiffEnabled()
	{
		return config.isDiffEnabled();
	}

	@Override
	public GuavaRequirements withDiff()
	{
		config.withDiff();
		return this;
	}

	@Override
	public GuavaRequirements withoutDiff()
	{
		config.withoutDiff();
		return this;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return config.getContext();
	}

	@Override
	public GuavaRequirements putContext(String name, Object value)
	{
		config.putContext(name, value);
		return this;
	}

	@Override
	public GuavaRequirements removeContext(String name)
	{
		config.removeContext(name);
		return this;
	}

	@Override
	public String toString(Object o)
	{
		return config.toString(o);
	}

	@Override
	public <T> GuavaRequirements withStringConverter(Class<T> type, Function<T, String> converter)
	{
		config.withStringConverter(type, converter);
		return this;
	}

	@Override
	public <T> GuavaRequirements withoutStringConverter(Class<T> type)
	{
		config.withoutStringConverter(type);
		return this;
	}

	@Override
	public GuavaRequirements withConfiguration(Configuration configuration)
	{
		this.config = configuration;
		return this;
	}
}
