/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Default implementation of {@link FloatingPointCapabilities} for {@code float}s.
 *
 * @author Gili Tzabari
 */
public final class PrimitiveFloatVerifierImpl
	extends FloatVerifierCapabilitiesImpl<PrimitiveFloatingPointVerifier<Float>>
	implements PrimitiveFloatingPointVerifier<Float>
{
	/**
	 * Creates new PrimitiveFloatVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveFloatVerifierImpl(ApplicationScope scope, Float actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
