/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractDoubleVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code FloatingPointVerifier<Double>}.
 */
public final class DoubleVerifierImpl
	extends AbstractDoubleVerifier<FloatingPointVerifier<Double>>
	implements FloatingPointVerifier<Double>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public DoubleVerifierImpl(ApplicationScope scope, String name, Double actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected FloatingPointVerifier<Double> getThis()
	{
		return this;
	}
}
