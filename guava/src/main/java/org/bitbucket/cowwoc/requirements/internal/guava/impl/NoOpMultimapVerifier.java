/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.guava.impl;

import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpObjectCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.impl.NoOpPrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;

/**
 * An implementation of {@code MultimapVerifier} that does nothing.
 *
 * @param <K> the type of keys in the multimap
 * @param <V> the type of values in the multimap
 * @author Gili Tzabari
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
	public CollectionVerifier<K> keySet()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<K>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<V> values()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> values(Consumer<CollectionVerifier<V>> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<Entry<K, V>> entries()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public MultimapVerifier<K, V> entries(Consumer<CollectionVerifier<Entry<K, V>>> consumer)
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
