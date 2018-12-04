/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.IntegerCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link IntegerCapabilities} for {@code long}s.
 */
public final class PrimitiveLongVerifierImpl
	extends IntegerVerifierCapabilitiesImpl<PrimitiveIntegerVerifier<Long>, Long>
	implements PrimitiveIntegerVerifier<Long>
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
	public PrimitiveLongVerifierImpl(ApplicationScope scope, String name, Long actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveIntegerVerifier<Long> getThis()
	{
		return this;
	}
}
