/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.java.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

/**
 * Default implementation of {@link PrimitiveNumberVerifier}.
 *
 * @param <T> the type of the value
 */
public final class PrimitiveNumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	/**
	 * Creates new PrimitiveNumberVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveNumberVerifierImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<T> isNull()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be null", name), null).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<T> isNotNull()
	{
		// Always true
		return this;
	}
}
