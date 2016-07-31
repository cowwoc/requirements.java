/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * An implementation of OptionalRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpOptionalRequirements implements OptionalRequirements
{
	INSTANCE;

	@Override
	public OptionalRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public OptionalRequirements isEmpty()
	{
		return this;
	}

	@Override
	public OptionalRequirements isPresent()
	{
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public OptionalRequirements isNull()
	{
		return this;
	}

	@Override
	public OptionalRequirements isNotNull()
	{
		return this;
	}

	@Override
	public OptionalRequirements isolate(Consumer<OptionalRequirements> consumer)
	{
		return this;
	}
}
