/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Default implementation of {@code PrimitiveNumberVerifier}.
 *
 * @param <T> the type of the value
 */
public final class PrimitiveNumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifier<PrimitiveNumberVerifier<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveNumberVerifierImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<T> isNull()
	{
		return neverNull();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<T> isNotNull()
	{
		return neverNull();
	}

	private PrimitiveNumberVerifier<T> neverNull()
	{
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " can never be null").
			build();
	}
}
