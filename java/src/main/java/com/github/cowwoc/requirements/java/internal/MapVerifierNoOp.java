/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.MapVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A {@code MapVerifier} that does nothing.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public final class MapVerifierNoOp<K, V>
	extends AbstractObjectVerifierNoOp<MapVerifier<K, V>, Map<K, V>>
	implements MapVerifier<K, V>
{
	private static final MapVerifierNoOp<?, ?> INSTANCE = new MapVerifierNoOp<>();

	/**
	 * @param <K> the type of keys in the map
	 * @param <V> the type of values in the map
	 * @return the singleton instance
	 */
	public static <K, V> MapVerifierNoOp<K, V> getInstance()
	{
		@SuppressWarnings("unchecked")
		MapVerifierNoOp<K, V> result = (MapVerifierNoOp<K, V>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private MapVerifierNoOp()
	{
	}

	@Override
	protected MapVerifier<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
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
	public SizeVerifier size()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public MapVerifier<K, V> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
