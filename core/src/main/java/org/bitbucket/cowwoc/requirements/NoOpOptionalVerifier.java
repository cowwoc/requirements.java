/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * An implementation of OptionalVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpOptionalVerifier implements OptionalVerifier
{
	INSTANCE;

	@Override
	public OptionalVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public OptionalVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public OptionalVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		return this;
	}

	@Override
	public OptionalVerifier isPresent()
	{
		return this;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalVerifier isIn(Collection<Optional<?>> collection)
	{
		return this;
	}

	@Override
	public OptionalVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNull()
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotNull()
	{
		return this;
	}

	@Override
	public OptionalVerifier isolate(Consumer<OptionalVerifier> consumer)
	{
		return this;
	}
}
