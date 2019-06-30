/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.MapVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

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
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public MapVerifierNoOp(Configuration config)
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
		return new CollectionVerifierNoOp<>(config);
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return new CollectionVerifierNoOp<>(config);
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return new CollectionVerifierNoOp<>(config);
	}

	@Override
	public MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer)
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
	public SizeVerifier size()
	{
		return new SizeVerifierNoOp(config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<SizeVerifier> consumer)
	{
		return this;
	}
}
