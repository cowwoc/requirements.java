/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.internal.MultimapVerifierImpl;
import org.bitbucket.cowwoc.requirements.guava.internal.NoOpMultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.util.function.Function;

/**
 * Default implementation of {@link GuavaVerifier}.
 */
public final class DefaultGuavaVerifier implements GuavaVerifier
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
	private final Configuration config;

	public DefaultGuavaVerifier()
	{
		this(MainApplicationScope.INSTANCE);
	}

	/**
	 * Equivalent to
	 * {@link #DefaultGuavaVerifier(ApplicationScope, Configuration)
	 * DefaultJavaVerifier(scope, scope.getGlobalConfiguration())}.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public DefaultGuavaVerifier(ApplicationScope scope)
	{
		this(verifyScope(scope), scope.getDefaultConfiguration().get());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private DefaultGuavaVerifier(ApplicationScope scope, Configuration configuration)
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
	public GuavaVerifier withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		verifyName(name);
		return new MultimapVerifierImpl<>(scope, name, actual, config);
	}

	/**
	 * @param name the name of the actual value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private void verifyName(String name)
	{
		if (name == null)
		{
			throw new ExceptionBuilder(scope, config, NullPointerException.class,
				"name may not be null").
				build();
		}
		if (name.trim().isEmpty())
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				"name may not be empty").
				build();
		}
	}

	@Override
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		if (config.assertionsAreEnabled())
			return requireThat(actual, name);
		return new NoOpMultimapVerifier<>(config);
	}

	@Override
	public GuavaVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier putContext(String name, Object value)
	{
		Configuration newConfig = config.putContext(name, value);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public GuavaVerifier removeContext(String name)
	{
		Configuration newConfig = config.removeContext(name);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public <T> GuavaVerifier withStringConverter(Class<T> type, Function<T, String> converter)
	{
		Configuration newConfig = config.withStringConverter(type, converter);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public <T> GuavaVerifier withoutStringConverter(Class<T> type)
	{
		Configuration newConfig = config.withoutStringConverter(type);
		if (newConfig.equals(config))
			return this;
		return new DefaultGuavaVerifier(scope, newConfig);
	}

	@Override
	public Configuration getConfiguration()
	{
		return config;
	}

	@Override
	public GuavaVerifier withConfiguration(Configuration configuration)
	{
		if (config.equals(configuration))
			return this;
		return new DefaultGuavaVerifier(scope, configuration);
	}
}
