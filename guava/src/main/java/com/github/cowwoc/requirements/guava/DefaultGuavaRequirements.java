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
 * <p>
 * This class is thread-safe.
 */
public final class DefaultGuavaRequirements implements GuavaRequirements
{
	static
	{
		GuavaSecrets.INSTANCE.setSecretRequirements(DefaultGuavaRequirements::new);
	}

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

	/**
	 * The application configuration.
	 */
	private final ApplicationScope scope;
	/**
	 * The instance configuration.
	 */
	private final Configuration config;

	/**
	 * Creates a default implementation of GuavaRequirements.
	 */
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
	private DefaultGuavaRequirements(ApplicationScope scope)
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
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public GuavaRequirements withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public boolean isCleanStackTrace()
	{
		return config.isCleanStackTrace();
	}

	@Override
	public GuavaRequirements withCleanStackTrace()
	{
		Configuration newConfig = config.withCleanStackTrace();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public GuavaRequirements withoutCleanStackTrace()
	{
		Configuration newConfig = config.withoutCleanStackTrace();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public boolean isDiffEnabled()
	{
		return config.isDiffEnabled();
	}

	@Override
	public GuavaRequirements withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public GuavaRequirements withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public Map<String, Object> getContext()
	{
		return config.getContext();
	}

	@Override
	@Deprecated
	public GuavaRequirements putContext(String name, Object value)
	{
		return withContext(name, value);
	}

	@Override
	public GuavaRequirements withContext(String name, Object value)
	{
		Configuration newConfig = config.withContext(name, value);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	@Deprecated
	public GuavaRequirements removeContext(String name)
	{
		return withoutContext(name);
	}

	@Override
	public GuavaRequirements withoutContext(String name)
	{
		Configuration newConfig = config.withoutContext(name);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public String createMessageWithContext(String message)
	{
		return config.createMessageWithContext(message);
	}

	@Override
	public String toString(Object value)
	{
		return config.toString(value);
	}

	@Override
	public <T> GuavaRequirements withStringConverter(Class<T> type, Function<T, String> converter)
	{
		Configuration newConfig = config.withStringConverter(type, converter);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public <T> GuavaRequirements withoutStringConverter(Class<T> type)
	{
		Configuration newConfig = config.withoutStringConverter(type);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, newConfig);
	}

	@Override
	public GuavaRequirements withConfiguration(Configuration configuration)
	{
		if (configuration.equals(config))
			return this;
		return new DefaultGuavaRequirements(scope, configuration);
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
