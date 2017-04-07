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
 *
 * @author Gili Tzabari
 */
public final class FloatVerifierImpl
	extends FloatVerifierCapabilitiesImpl<FloatingPointVerifier<Float>>
	implements FloatingPointVerifier<Float>
{
	/**
	 * Creates new FloatVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public FloatVerifierImpl(ApplicationScope scope, Float actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	protected FloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
