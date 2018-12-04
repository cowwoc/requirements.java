/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.IntegerCapabilities;

/**
 * Default implementation of {@link IntegerCapabilities} for {@code int}s.
 */
public final class PrimitiveIntegerVerifierImpl
	extends IntegerVerifierCapabilitiesImpl<PrimitiveIntegerVerifier<Integer>, Integer>
	implements PrimitiveIntegerVerifier<Integer>
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
	public PrimitiveIntegerVerifierImpl(ApplicationScope scope, String name, Integer actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveIntegerVerifier<Integer> getThis()
	{
		return this;
	}
}
