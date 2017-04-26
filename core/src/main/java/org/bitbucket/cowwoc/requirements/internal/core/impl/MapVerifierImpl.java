/*
 * Copyright 2013 Gili Tzabari.
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
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of MapVerifier.
 *
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public final class MapVerifierImpl<K, V>
	extends ObjectCapabilitiesImpl<MapVerifier<K, V>, Map<K, V>>
	implements MapVerifier<K, V>
{
	/**
	 * Creates new MapVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public MapVerifierImpl(ApplicationScope scope, Map<K, V> actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public CollectionVerifier<Set<K>, K> keySet()
	{
		return new CollectionVerifierImpl<>(scope, actual.keySet(), name + ".keySet()", Pluralizer.KEY,
			config);
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
		return new CollectionVerifierImpl<>(scope, actual.values(), name + ".values()",
			Pluralizer.VALUE, config);
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
		return new CollectionVerifierImpl<>(scope, actual.entrySet(), name + ".entrySet()",
			Pluralizer.ENTRY, config);
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
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public MapVerifier<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.size(), name,
			name + ".size()", Pluralizer.ENTRY, config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(size());
		return this;
	}
}
