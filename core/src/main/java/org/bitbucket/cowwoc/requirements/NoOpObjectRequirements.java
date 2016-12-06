/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of ObjectRequirements that does nothing.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class NoOpObjectRequirements<T> implements ObjectRequirements<T>
{
	NoOpObjectRequirements()
	{
	}

	@Override
	public ObjectRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isNull()
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isNotNull()
	{
		return this;
	}

	@Override
	public ObjectRequirements<T> isolate(Consumer<ObjectRequirements<T>> consumer)
	{
		return this;
	}
}
