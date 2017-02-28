/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;

/**
 * An implementation of {@code MapVerifier} that does nothing.
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
	public PrimitiveIntegerVerifier size()
	{
		return new NoOpPrimitiveIntegerVerifier(config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		return this;
	}
}
