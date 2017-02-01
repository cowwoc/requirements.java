/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of MapVerifier that does nothing.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 * @author Gili Tzabari
 */
public final class NoOpMapVerifier<K, V> implements MapVerifier<K, V>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpMapVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public CollectionVerifier<K> keySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<K>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<V> values()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<V>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Entry<K, V>> entrySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Entry<K, V>>> consumer)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isEqualTo(Map<K, V> value)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isEqualTo(Map<K, V> value, String name)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotEqualTo(Map<K, V> value)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotEqualTo(Map<K, V> value, String name)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isIn(Collection<Map<K, V>> collection)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isNull()
	{
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotNull()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier size()
	{
		return new NoOpPrimitiveIntegerVerifier(config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public MapVerifier<K, V> asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Map<K, V>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Map<K, V> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}
}
