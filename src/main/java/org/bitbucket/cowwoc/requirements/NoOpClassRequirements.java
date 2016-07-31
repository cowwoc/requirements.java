/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of ClassRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpClassRequirements implements ClassRequirements<Object>
{
	INSTANCE;

	@Override
	public ClassRequirements<Object> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isSupertypeOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isEqualTo(Class<Object> value)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isEqualTo(Class<Object> value, String name)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isNotEqualTo(Class<Object> value)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isNotEqualTo(Class<Object> value, String name)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isNull()
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isNotNull()
	{
		return this;
	}

	@Override
	public ClassRequirements<Object> isolate(Consumer<ClassRequirements<Object>> consumer)
	{
		return this;
	}
}
