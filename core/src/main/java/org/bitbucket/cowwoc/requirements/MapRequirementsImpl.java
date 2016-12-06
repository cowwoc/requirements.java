/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of MapRequirements.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapRequirementsImpl<K, V> implements MapRequirements<K, V>
{
	private final SingletonScope scope;
	private final Map<K, V> actual;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Map<K, V>> asObject;

	/**
	 * Creates new MapRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	MapRequirementsImpl(SingletonScope scope, Map<K, V> actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(scope, actual, name, config);
	}

	@Override
	public MapRequirements<K, V> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> isEqualTo(Map<K, V> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public MapRequirements<K, V> isEqualTo(Map<K, V> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotEqualTo(Map<K, V> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotEqualTo(Map<K, V> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public MapRequirements<K, V> isIn(Collection<Map<K, V>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public MapRequirements<K, V> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<K> keySet()
	{
		return new CollectionRequirementsImpl<>(scope, actual.keySet(), name + ".keySet()", config,
			Pluralizer.KEY);
	}

	@Override
	public CollectionRequirements<V> values()
	{
		return new CollectionRequirementsImpl<>(scope, actual.values(), name + ".values()", config,
			Pluralizer.VALUE);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> entrySet()
	{
		return new CollectionRequirementsImpl<>(scope, actual.entrySet(), name + ".entrySet()",
			config, Pluralizer.ENTRY);
	}

	@Override
	public MapRequirements<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public MapRequirements<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public ContainerSizeRequirements size()
	{
		return new ContainerSizeRequirementsImpl(scope, actual, actual.size(), name,
			name + ".size()", Pluralizer.ENTRY, config);
	}

	@Override
	public MapRequirements<K, V> isolate(Consumer<MapRequirements<K, V>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
