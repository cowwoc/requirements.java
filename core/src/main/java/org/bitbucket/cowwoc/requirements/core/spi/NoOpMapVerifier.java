/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of MapVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpMapVerifier implements MapVerifier<Object, Object>
{
	// Enum declares values() which conflicts with Map.values()
	public static final NoOpMapVerifier INSTANCE = new NoOpMapVerifier();

	/**
	 * Prevent construction.
	 */
	private NoOpMapVerifier()
	{
	}

	@Override
	public MapVerifier<Object, Object> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Object> keySet()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public CollectionVerifier<Object> values()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public CollectionVerifier<Entry<Object, Object>> entrySet()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public MapVerifier<Object, Object> isEmpty()
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isEqualTo(Map<Object, Object> value)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isEqualTo(Map<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isNotEqualTo(Map<Object, Object> value)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isNotEqualTo(Map<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isIn(Collection<Map<Object, Object>> collection)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isNull()
	{
		return this;
	}

	@Override
	public MapVerifier<Object, Object> isNotNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier size()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public Optional<Map<Object, Object>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Map<Object, Object> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public MapVerifier<Object, Object> isolate(Consumer<MapVerifier<Object, Object>> consumer)
	{
		return this;
	}
}
