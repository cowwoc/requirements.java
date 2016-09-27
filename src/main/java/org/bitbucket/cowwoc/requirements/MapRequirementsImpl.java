/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Configuration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Default implementation of MapRequirements.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapRequirementsImpl<K, V> implements MapRequirements<K, V>
{
	private final Map<K, V> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Map<K, V>> asObject;

	/**
	 * Creates new MapRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapRequirementsImpl(Map<K, V> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public MapRequirements<K, V> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public MapRequirements<K, V> isEqualTo(Map<K, V> value) throws IllegalArgumentException
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public MapRequirements<K, V> isEqualTo(Map<K, V> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotEqualTo(Map<K, V> value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotEqualTo(Map<K, V> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public MapRequirements<K, V> isIn(Collection<Map<K, V>> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public MapRequirements<K, V> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public MapRequirements<K, V> isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public MapRequirements<K, V> isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<K> keySet()
	{
		return new MapKeySetRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public CollectionRequirements<V> values()
	{
		return new MapValuesRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> entrySet()
	{
		return new MapEntrySetRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public MapRequirements<K, V> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public MapRequirements<K, V> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public MapSizeRequirements size()
	{
		return new MapSizeRequirementsImpl(parameter, name, config);
	}

	@Override
	public MapRequirements<K, V> isolate(Consumer<MapRequirements<K, V>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
