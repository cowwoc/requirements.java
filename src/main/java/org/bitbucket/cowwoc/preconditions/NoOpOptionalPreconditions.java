/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * An implementation of OptionalPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpOptionalPreconditions implements OptionalPreconditions
{
	INSTANCE;

	@Override
	public OptionalPreconditions isEmpty()
	{
		return this;
	}

	@Override
	public OptionalPreconditions isPresent()
	{
		return this;
	}

	@Override
	public NoOpOptionalPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public OptionalPreconditions isEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public OptionalPreconditions isNull()
	{
		return this;
	}

	@Override
	public OptionalPreconditions isNotNull()
	{
		return this;
	}
}
