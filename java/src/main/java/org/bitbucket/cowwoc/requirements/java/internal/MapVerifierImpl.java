/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.MapVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of MapVerifier.
 *
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 */
public final class MapVerifierImpl<K, V>
	extends ObjectCapabilitiesImpl<MapVerifier<K, V>, Map<K, V>>
	implements MapVerifier<K, V>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public MapVerifierImpl(ApplicationScope scope, String name, Map<K, V> actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return new CollectionVerifierImpl<>(scope, name + ".keySet()", actual.keySet(), Pluralizer.KEY, config);
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer)
	{
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<V>, V> values()
	{
		return new CollectionVerifierImpl<>(scope, name + ".values()", actual.values(), Pluralizer.VALUE, config);
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer)
	{
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return new CollectionVerifierImpl<>(scope, name + ".entrySet()", actual.entrySet(), Pluralizer.ENTRY,
			config);
	}

	@Override
	public MapVerifier<K, V> entrySet(
		Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		consumer.accept(entrySet());
		return this;
	}

	@Override
	public MapVerifier<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be empty.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public MapVerifier<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be empty").
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".size()", actual.size(),
			Pluralizer.ENTRY, config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(size());
		return this;
	}
}
