/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * An implementation of {@code ExtensibleBooleanVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 */
public abstract class AbstractBooleanVerifier<S> extends AbstractComparableVerifier<S, Boolean>
	implements ExtensibleBooleanVerifier<S>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractBooleanVerifier(ApplicationScope scope, String name, Boolean actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isTrue()
	{
		if (actual)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be true").
			build();
	}

	@Override
	public S isFalse()
	{
		if (!actual)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be false").
			build();
	}
}
