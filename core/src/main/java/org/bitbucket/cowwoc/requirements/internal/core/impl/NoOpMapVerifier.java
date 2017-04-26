/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;

/**
 * An implementation of {@link MapVerifier} that does nothing.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 * @author Gili Tzabari
 */
public final class NoOpMapVerifier<K, V>
	extends NoOpObjectCapabilities<MapVerifier<K, V>, Map<K, V>>
	implements MapVerifier<K, V>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpMapVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected MapVerifier<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> entrySet(
		Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer)
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
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		return this;
	}
}
