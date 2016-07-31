/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.function.Consumer;

/**
 * An implementation of ObjectRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpObjectRequirements implements ObjectRequirements<NoOpObjectRequirements, Object>
{
	INSTANCE;

	@Override
	public NoOpObjectRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isNotEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isNull()
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isNotNull()
	{
		return this;
	}

	@Override
	public NoOpObjectRequirements isolate(Consumer<NoOpObjectRequirements> consumer)
	{
		return this;
	}
}
