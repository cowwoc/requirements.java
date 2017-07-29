/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.guava.impl;

import java.util.function.Function;
import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.guava.GuavaVerifiers;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * A placeholder that is meant to be replaced by the requirements-guava module.
 *
 * @author Gili Tzabari
 */
public final class GuavaVerifiersImpl implements GuavaVerifiers, Configurable
{
	/**
	 * Creates new verifiers. This constructor is meant to be used by automated tests, not by users.
	 *
	 * @param scope         the application configuration
	 * @param configuration the instance configuration
	 * @throws AssertionError if any of the arguments are null
	 */
	public GuavaVerifiersImpl(ApplicationScope scope, Configuration configuration)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withAssertionsEnabled()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withAssertionsDisabled()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withException(Class<? extends RuntimeException> exception)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withDefaultException()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withDiff()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withoutDiff()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers addContext(String name, Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> GuavaVerifiers withStringConverter(Class<T> type, Function<T, String> converter)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> GuavaVerifiers withoutStringConverter(Class<T> type)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GuavaVerifiers withConfiguration(Configuration configuration)
	{
		throw new UnsupportedOperationException();
	}
}
