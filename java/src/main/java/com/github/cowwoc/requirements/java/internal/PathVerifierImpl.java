/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PathVerifier;
import com.github.cowwoc.requirements.java.PathValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Default implementation of {@code PathVerifier}.
 */
public final class PathVerifierImpl extends AbstractObjectVerifier<PathVerifier, PathValidator, Path>
	implements PathVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PathVerifierImpl(PathValidator validator)
	{
		super(validator);
	}

	@Override
	protected PathVerifier getThis()
	{
		return this;
	}

	@Override
	public PathVerifier exists()
	{
		validator.exists();
		return validationResult();
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options) throws IOException
	{
		validator.isRegularFile(options);
		return validationResult(IOException.class);
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options) throws IOException
	{
		validator.isDirectory(options);
		return validationResult(IOException.class);
	}

	@Override
	public PathVerifier isRelative()
	{
		validator.isRelative();
		return validationResult();
	}

	@Override
	public PathVerifier isAbsolute()
	{
		validator.isAbsolute();
		return validationResult();
	}
}
