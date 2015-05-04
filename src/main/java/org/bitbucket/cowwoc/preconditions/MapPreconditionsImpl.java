/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Map;

/**
 * Default implementation of MapPreconditions.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapPreconditionsImpl<K, V> extends ObjectPreconditionsImpl<MapPreconditions<K, V>, Map<K, V>>
	implements MapPreconditions<K, V>
{
	/**
	 * Creates new MapPreconditionsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapPreconditionsImpl(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	@Override
	public MapPreconditions<K, V> containsKey(K key) throws IllegalArgumentException
	{
		if (parameter.containsKey(key))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain key: %s. Was: %s", name, key, parameter));
	}

	@Override
	public MapPreconditions<K, V> containsValue(V value) throws IllegalArgumentException
	{
		if (parameter.containsValue(value))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain value: %s. Was: %s", name, value, parameter));
	}

	@Override
	public MapPreconditions<K, V> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	@Override
	public MapPreconditions<K, V> sizeEquals(int size) throws IllegalArgumentException
	{
		if (parameter.size() == size)
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %d elements. Was: %d", name, size, parameter.size()));
	}
}
