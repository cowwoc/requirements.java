/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.IntegerCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@link IntegerCapabilities} for {@code long}s.
 */
public final class PrimitiveLongVerifierImpl
	extends IntegerVerifierCapabilitiesImpl<PrimitiveIntegerVerifier<Long>, Long>
	implements PrimitiveIntegerVerifier<Long>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected PrimitiveLongVerifierImpl(ApplicationScope scope, String name, Long actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected PrimitiveIntegerVerifier<Long> getThis()
	{
		return this;
	}
}
