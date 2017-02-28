/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;

/**
 * An implementation of {@code OptionalVerifier} that does nothing.
 *
 * @author Gili Tzabari
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
