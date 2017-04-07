/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link PrimitiveNumberVerifier}.
 * <p>
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public class PrimitiveNumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	/**
	 * Creates new PrimitiveNumberVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveNumberVerifierImpl(ApplicationScope scope, T actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
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
