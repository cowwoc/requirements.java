/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.MapVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of MapVerifier.
 *
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public final class MapVerifierImpl<K, V> implements MapVerifier<K, V>
{
	private final ApplicationScope scope;
	private final Map<K, V> actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<Map<K, V>> asObject;

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
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public MapVerifier<K, V> isEqualTo(Map<K, V> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public MapVerifier<K, V> isEqualTo(Map<K, V> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotEqualTo(Map<K, V> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotEqualTo(Map<K, V> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public MapVerifier<K, V> isIn(Collection<Map<K, V>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public MapVerifier<K, V> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public MapVerifier<K, V> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public MapVerifier<K, V> isNotNull()
	{
		asObject.isNotNull();
		return this;
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
	public ContainerSizeVerifier size()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.size(), name,
			name + ".size()", Pluralizer.ENTRY, config);
	}

	@Override
	public MapVerifier<K, V> size(Consumer<ContainerSizeVerifier> consumer)
	{
		consumer.accept(size());
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public MapVerifier<K, V> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<Map<K, V>> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public Map<K, V> getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public MapVerifier<K, V> configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
