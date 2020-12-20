/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PathValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;

/**
 * A {@code PathValidator} that does nothing.
 */
public final class PathValidatorNoOp
	extends AbstractObjectValidatorNoOp<PathValidator, Path>
	implements PathValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	PathValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PathValidator getThis()
	{
		return this;
	}

	@Override
	public PathValidator exists()
	{
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		return this;
	}

	@Override
	public PathValidator isDirectory(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathValidator isRelative()
	{
		return this;
	}
}
