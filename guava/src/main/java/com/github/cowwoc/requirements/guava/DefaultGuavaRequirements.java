/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.guava.internal.MultimapValidatorImpl;
import com.github.cowwoc.requirements.guava.internal.MultimapVerifierImpl;
import com.github.cowwoc.requirements.guava.internal.MultimapVerifierNoOp;
import com.github.cowwoc.requirements.guava.internal.secrets.GuavaSecrets;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Verifiers;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

/**
 * Default implementation of GuavaRequirements.
 */
public final class DefaultGuavaRequirements implements GuavaRequirements
{
	static
	{
		GuavaSecrets.INSTANCE.setSecretRequirements(DefaultGuavaRequirements::new);
	}

	/**
	 * The application configuration.
	 */
	private final ApplicationScope scope;
	/**
	 * The instance configuration.
	 */
	private Configuration config;

	/**
	 * Creates a default implementation of GuavaRequirements.
	 */
	public DefaultGuavaRequirements()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * This constructor is meant to be used by secrets classes, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultGuavaRequirements(ApplicationScope scope)
	{
		this(scope, scope.getDefaultConfiguration().get());
	}

	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultGuavaRequirements(ApplicationScope scope, Configuration config)
	{
		assert (scope != null) : "scope may not be null";
		assert (config != null) : "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	@Override
	public GuavaRequirements copy()
	{
		return new DefaultGuavaRequirements(scope, config.copy());
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
	public boolean isCleanStackTrace()
	{
		return config.isCleanStackTrace();
	}

	@Override
	public GuavaRequirements withCleanStackTrace()
	{
		config.withCleanStackTrace();
		return this;
	}

	@Override
	public GuavaRequirements withoutCleanStackTrace()
	{
		config.withoutCleanStackTrace();
		return this;
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
	public GuavaRequirements withContext(String name, Object value)
	{
		config.withContext(name, value);
		return this;
	}

	@Override
	public GuavaRequirements withoutContext(String name)
	{
		config.withoutContext(name);
		return this;
	}

	@Override
	public GuavaRequirements withoutAnyContext()
	{
		config.withoutAnyContext();
		return this;
	}

	@Override
	public String toString(Object value)
	{
		return config.toString(value);
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

	@Override
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		return new MultimapVerifierImpl<>(validateThat(actual, name));
	}

	@Override
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return MultimapVerifierNoOp.getInstance();
	}

	@Override
	public <K, V> MultimapValidator<K, V> validateThat(Multimap<K, V> actual, String name)
	{
		Verifiers.verifyName(scope, config, name);
		return new MultimapValidatorImpl<>(scope, config, name, actual, new ArrayList<>());
	}
}