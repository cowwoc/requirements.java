/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;

import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * An implementation of {@link PathVerifier} that does nothing.
 */
public final class NoOpPathVerifier
	extends NoOpObjectCapabilities<PathVerifier, Path>
	implements PathVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPathVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PathVerifier getThis()
	{
		return this;
	}

	@Override
	public PathVerifier exists()
	{
		return this;
	}

	@Override
	public PathVerifier isAbsolute()
	{
		return this;
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathVerifier isRelative()
	{
		return this;
	}
}
