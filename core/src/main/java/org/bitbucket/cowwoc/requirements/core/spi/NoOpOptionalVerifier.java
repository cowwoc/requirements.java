/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of OptionalVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpOptionalVerifier implements OptionalVerifier
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
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public Optional<Optional<?>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Optional<?> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public OptionalVerifier isolate(Consumer<OptionalVerifier> consumer)
	{
		return this;
	}
}
