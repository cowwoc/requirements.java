/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link FloatingPointCapabilities} for {@code float}s.
 */
public final class PrimitiveFloatVerifierImpl
	extends FloatVerifierCapabilitiesImpl<PrimitiveFloatingPointVerifier<Float>>
	implements PrimitiveFloatingPointVerifier<Float>
{
	/**
	 * Creates new PrimitiveFloatVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveFloatVerifierImpl(ApplicationScope scope, String name, Float actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
