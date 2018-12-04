/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link FloatingPointVerifier FloatingPointVerifier&lt;Float&gt;}.
 */
public final class FloatVerifierImpl
	extends FloatVerifierCapabilitiesImpl<FloatingPointVerifier<Float>>
	implements FloatingPointVerifier<Float>
{
	/**
	 * Creates new FloatVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public FloatVerifierImpl(ApplicationScope scope, String name, Float actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected FloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
