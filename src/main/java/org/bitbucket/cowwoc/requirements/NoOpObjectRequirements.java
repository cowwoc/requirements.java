/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of ObjectRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpObjectRequirements implements ObjectRequirements<Object>
{
	INSTANCE;

	@Override
	public ObjectRequirements<Object> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isNotEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isNull()
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isNotNull()
	{
		return this;
	}

	@Override
	public ObjectRequirements<Object> isolate(Consumer<ObjectRequirements<Object>> consumer)
	{
		return this;
	}
}
