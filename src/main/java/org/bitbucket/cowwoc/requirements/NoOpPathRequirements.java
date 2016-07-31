/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * An implementation of PathRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpPathRequirements implements PathRequirements
{
	INSTANCE;

	@Override
	public PathRequirements exists()
	{
		return this;
	}

	@Override
	public PathRequirements isAbsolute()
	{
		return this;
	}

	@Override
	public PathRequirements isDirectory(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathRequirements isRegularFile(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathRequirements isRelative()
	{
		return this;
	}

	@Override
	public NoOpPathRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value)
	{
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value, String name)
	{
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value)
	{
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value, String name)
	{
		return this;
	}

	@Override
	public PathRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public PathRequirements isNull()
	{
		return this;
	}

	@Override
	public PathRequirements isNotNull()
	{
		return this;
	}

	@Override
	public PathRequirements isolate(Consumer<PathRequirements> consumer)
	{
		return this;
	}
}
