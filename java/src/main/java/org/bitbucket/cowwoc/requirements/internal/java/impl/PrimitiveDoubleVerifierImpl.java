/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;

/**
 * Default implementation of {@link FloatingPointCapabilities} for {@code double}s.
 */
public final class PrimitiveDoubleVerifierImpl
	extends DoubleVerifierCapabilitiesImpl<PrimitiveFloatingPointVerifier<Double>>
	implements PrimitiveFloatingPointVerifier<Double>
{
	/**
	 * Creates new PrimitiveDoubleVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveDoubleVerifierImpl(ApplicationScope scope, String name, Double actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Double> getThis()
	{
		return this;
	}
}
