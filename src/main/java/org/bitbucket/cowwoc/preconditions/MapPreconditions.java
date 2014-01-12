/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Map;

/**
 * Verifies preconditions of a Map parameter.
 * <p/>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public final class MapPreconditions<K, V> extends Preconditions<MapPreconditions<K, V>, Map<K, V>>
{
	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapPreconditions(String name, Map<K, V> parameter)
	{
		super(name, parameter);
	}

	/**
	 * Ensures that the parameter contains a key.
	 * <p/>
	 * @param key the key that must exist
	 * @return this
	 * @throws IllegalArgumentException if the map does not contain key
	 */
	public MapPreconditions<K, V> containsKey(K key) throws IllegalArgumentException
	{
		if (!parameter.containsKey(key))
			throw new IllegalArgumentException(name + " must contain key: " + key + ". Was: " + parameter);
		return this;
	}

	/**
	 * Ensures that the parameter contains a value.
	 * <p/>
	 * @param value the value that must exist
	 * @return this
	 * @throws IllegalArgumentException if the map does not contain value
	 */
	public MapPreconditions<K, V> containsValue(V value) throws IllegalArgumentException
	{
		if (!parameter.containsValue(value))
		{
			throw new IllegalArgumentException(name + " must contain value: " + value + ". Was: " +
				parameter);
		}
		return this;
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	public MapPreconditions<K, V> isNotEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			throw new IllegalArgumentException(name + " may not be empty");
		return this;
	}
}
