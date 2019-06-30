/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PathVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * A {@code PathVerifier} that does nothing.
 */
public final class PathVerifierNoOp
	extends AbstractObjectVerifierNoOp<PathVerifier, Path>
	implements PathVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PathVerifierNoOp(Configuration config)
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
