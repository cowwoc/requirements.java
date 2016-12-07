/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;

/**
 * An implementation of ObjectVerifier that does nothing.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public final class NoOpObjectVerifier<T> implements ObjectVerifier<T>
{
	public NoOpObjectVerifier()
	{
	}

	@Override
	public ObjectVerifier<T> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isolate(Consumer<ObjectVerifier<T>> consumer)
	{
		return this;
	}
}
