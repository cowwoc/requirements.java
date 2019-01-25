/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;

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
	public OptionalVerifier contains(Object value, String name)
	{
		return null;
	}
}
