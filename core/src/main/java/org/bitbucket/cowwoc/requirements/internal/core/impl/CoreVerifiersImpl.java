/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.function.Supplier;
import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.impl.AbstractCoreVerifiers;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ToStringSupplier;

/**
 * Default implementation of {@link CoreVerifiers}.
 *
 * @author Gili Tzabari
 */
public final class CoreVerifiersImpl extends AbstractCoreVerifiers
{
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

	/**
	 * Creates new verifiers.
	 */
	public CoreVerifiersImpl()
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
	private CoreVerifiersImpl(ApplicationScope scope)
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
	private CoreVerifiersImpl(ApplicationScope scope, Configuration configuration)
	{
		super(scope, configuration);
	}

	@Override
	public CoreVerifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public CoreVerifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public CoreVerifiers addContext(String key, Object value)
	{
		return new CoreVerifiersImpl(scope, config.addContext(key, value));
	}

	@Override
	public CoreVerifiers addContext(String key, Supplier<String> value)
	{
		return addContext(key, new ToStringSupplier(value));
	}

	@Override
	public CoreVerifiers withAssertionsEnabled()
	{
		Configuration newConfig = config.withAssertionsEnabled();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public CoreVerifiers withAssertionsDisabled()
	{
		Configuration newConfig = config.withAssertionsDisabled();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public Configurable withDiff()
	{
		Configuration newConfig = config.withDiff();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public Configurable withoutDiff()
	{
		Configuration newConfig = config.withoutDiff();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiersImpl(scope, newConfig);
	}

	@Override
	public Configurable withConfiguration(Configuration configuration)
	{
		if (config.equals(configuration))
			return this;
		return new CoreVerifiersImpl(scope, configuration);
	}
}
