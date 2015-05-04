/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * An implementation of PathPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpPathPreconditions implements PathPreconditions
{
	INSTANCE;

	@Override
	public PathPreconditions exists()
	{
		return this;
	}

	@Override
	public PathPreconditions isAbsolute()
	{
		return this;
	}

	@Override
	public PathPreconditions isDirectory(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathPreconditions isRegularFile(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathPreconditions isRelative()
	{
		return this;
	}

	@Override
	public <E extends RuntimeException> PathPreconditions using(Class<E> exception)
	{
		return this;
	}

	@Override
	public PathPreconditions isEqualTo(Path value)
	{
		return this;
	}

	@Override
	public PathPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public PathPreconditions isNull()
	{
		return this;
	}

	@Override
	public PathPreconditions isNotNull()
	{
		return this;
	}
}
