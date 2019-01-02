/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@link FloatingPointCapabilities} for {@code float}s.
 */
public final class PrimitiveFloatVerifierImpl
	extends FloatVerifierCapabilitiesImpl<PrimitiveFloatingPointVerifier<Float>>
	implements PrimitiveFloatingPointVerifier<Float>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected PrimitiveFloatVerifierImpl(ApplicationScope scope, String name, Float actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
