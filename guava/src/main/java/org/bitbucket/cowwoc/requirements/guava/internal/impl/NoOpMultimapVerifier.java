/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.internal.impl;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.impl.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.impl.NoOpObjectCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.impl.NoOpPrimitiveNumberVerifier;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An implementation of {@code MultimapVerifier} that does nothing.
 *
 * @param <K> the type of keys in the multimap
 * @param <V> the type of values in the multimap
 */
public final class NoOpMultimapVerifier<K, V>
	extends NoOpObjectCapabilities<MultimapVerifier<K, V>, Multimap<K, V>>
	implements MultimapVerifier<K, V>
{
	/**
	 * @param config the verifier's configuration
	 */
	public NoOpMultimapVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected MultimapVerifier<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> entries(
		Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		return this;
	}
}
