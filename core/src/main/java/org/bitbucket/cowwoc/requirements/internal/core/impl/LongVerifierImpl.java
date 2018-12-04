/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.IntegerVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link IntegerVerifier IntegerVerifier&lt;Long&gt;}.
 */
public final class LongVerifierImpl
	extends IntegerVerifierCapabilitiesImpl<IntegerVerifier<Long>, Long>
	implements IntegerVerifier<Long>
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
	public LongVerifierImpl(ApplicationScope scope, String name, Long actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	protected IntegerVerifier<Long> getThis()
	{
		return this;
	}
}
