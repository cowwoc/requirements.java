/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;

import java.util.Optional;

/**
 * An implementation of {@link OptionalVerifier} that does nothing.
 */
public final class NoOpOptionalVerifier
	extends NoOpObjectCapabilities<OptionalVerifier, Optional<?>>
	implements OptionalVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpOptionalVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected OptionalVerifier getThis()
	{
		return this;
	}

	@Override
	public OptionalVerifier isPresent()
	{
		return this;
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(String name, Object value)
	{
		return null;
	}
}
