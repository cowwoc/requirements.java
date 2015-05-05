/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Map;

/**
 * An implementation of MapPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpMapPreconditions implements MapPreconditions<Object, Object>
{
	INSTANCE;

	@Override
	public MapPreconditions<Object, Object> containsKey(Object key)
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> containsValue(Object value)
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public NoOpMapPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> isEqualTo(Map<Object, Object> value)
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> isNull()
	{
		return this;
	}

	@Override
	public MapPreconditions<Object, Object> isNotNull()
	{
		return this;
	}
}
