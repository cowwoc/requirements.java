/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.impl;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpStringVerifier;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;

/**
 * @param <K> the type of keys in the multimap
 * @param <V> the type of values in the multimap
 * @author Gili Tzabari
 */
public final class NoOpMultimapVerifier<K, V> implements MultimapVerifier<K, V>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 */
	public NoOpMultimapVerifier(Configuration config)
	{
		this.config = config;
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
	public ContainerSizeVerifier size()
	{
		return new NoOpContainerSizeVerifier(config);
	}

	@Override
	public MultimapVerifier<K, V> isEqualTo(Multimap<K, V> value)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isEqualTo(Multimap<K, V> value, String name)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNotEqualTo(Multimap<K, V> value)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNotEqualTo(Multimap<K, V> value, String name)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isIn(Collection<Multimap<K, V>> collection)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNull()
	{
		return this;
	}

	@Override
	public MultimapVerifier<K, V> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public MultimapVerifier<K, V> asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Multimap<K, V>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Multimap<K, V> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public MultimapVerifier<K, V> configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
