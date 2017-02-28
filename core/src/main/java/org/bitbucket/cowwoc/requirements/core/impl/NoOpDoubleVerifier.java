/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.DoubleVerifier;

/**
 * An implementation of {@code DoubleVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpDoubleVerifier
	extends NoOpNumberCapabilities<DoubleVerifier, Double>
	implements DoubleVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpDoubleVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected DoubleVerifier getThis()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isFinite()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotFinite()
	{
		return this;
	}
}
