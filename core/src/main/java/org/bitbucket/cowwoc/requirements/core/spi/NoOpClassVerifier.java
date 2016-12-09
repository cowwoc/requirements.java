/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ClassVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpClassVerifier implements ClassVerifier<Object>
{
	INSTANCE;

	@Override
	public ClassVerifier<Object> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isSupertypeOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isEqualTo(Class<Object> value)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isEqualTo(Class<Object> value, String name)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isNotEqualTo(Class<Object> value)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isNotEqualTo(Class<Object> value, String name)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isIn(Collection<Class<Object>> collection)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isNull()
	{
		return this;
	}

	@Override
	public ClassVerifier<Object> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public ClassVerifier<Object> isolate(Consumer<ClassVerifier<Object>> consumer)
	{
		return this;
	}
}
