/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.java.Configurable;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifiers;
import org.bitbucket.cowwoc.requirements.java.impl.AbstractJavaVerifiers;
import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.java.scope.MainApplicationScope;

import java.util.function.Function;

/**
 * Default implementation of {@link JavaVerifiers}.
 */
public final class JavaVerifiersImpl extends AbstractJavaVerifiers
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

	/**
	 * Creates new verifiers.
	 */
	public JavaVerifiersImpl()
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
	private JavaVerifiersImpl(ApplicationScope scope)
	{
		this(verifyScope(scope), scope.getDefaultConfiguration().get());
	}

	/**
	 * Creates new verifiers. This constructor is meant to be used by automated tests, not by users.
	 *
	 * @param scope         the application configuration
	 * @param configuration the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	private JavaVerifiersImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	@Override
	public JavaVerifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public JavaVerifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public JavaVerifiers addContext(String name, Object value)
	{
		return new JavaVerifiersImpl(scope, config.addContext(name, value));
	}

	@Override
	public JavaVerifiers withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public JavaVerifiers withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public JavaVerifiers withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public JavaVerifiers withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public <T> JavaVerifiers withStringConverter(Class<T> type, Function<T, String> converter)
	{
		Configuration newConfig = config.withStringConverter(type, converter);
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public <T> JavaVerifiers withoutStringConverter(Class<T> type)
	{
		Configuration newConfig = config.withoutStringConverter(type);
		if (newConfig.equals(config))
			return this;
		return new JavaVerifiersImpl(scope, newConfig);
	}

	@Override
	public Configurable withConfiguration(Configuration configuration)
	{
		if (config.equals(configuration))
			return this;
		return new JavaVerifiersImpl(scope, configuration);
	}
}
