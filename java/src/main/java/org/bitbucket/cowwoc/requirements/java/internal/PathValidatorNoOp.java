/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PathValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PathValidatorNoOp(ApplicationScope scope, Configuration config, List<ValidationFailure> failures)
	{
		super(scope, config, failures);
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
