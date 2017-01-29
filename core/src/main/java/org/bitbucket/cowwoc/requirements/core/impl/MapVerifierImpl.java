/*
 * Copyright 2013 Gili Tzabari.
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
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

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
	public CollectionVerifier<K> keySet()
	{
		return new CollectionVerifierImpl<>(scope, actual.keySet(), name + ".keySet()", Pluralizer.KEY,
			config);
	}

	@Override
	public MapVerifier<K, V> keySet(Consumer<CollectionVerifier<K>> consumer)
	{
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionVerifier<V> values()
	{
		return new CollectionVerifierImpl<>(scope, actual.values(), name + ".values()",
			Pluralizer.VALUE, config);
	}

	@Override
	public MapVerifier<K, V> values(Consumer<CollectionVerifier<V>> consumer)
	{
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionVerifier<Entry<K, V>> entrySet()
	{
		return new CollectionVerifierImpl<>(scope, actual.entrySet(), name + ".entrySet()",
			Pluralizer.ENTRY, config);
	}

	@Override
	public MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Entry<K, V>>> consumer)
	{
		consumer.accept(entrySet());
		return this;
	}

	@Override
	public MapVerifier<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public MapVerifier<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public PrimitiveIntegerVerifier size()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.size(), name,
			name + ".size()", Pluralizer.ENTRY, config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		consumer.accept(size());
		return this;
	}
}
