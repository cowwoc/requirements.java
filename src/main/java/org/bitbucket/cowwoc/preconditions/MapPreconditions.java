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
 * @author Gili Tzabari
 */
public final class MapPreconditions<K> extends Preconditions<Map<K, ?>>
{
	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	MapPreconditions(String name, Map<K, ?> parameter)
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
	public MapPreconditions<K> containsKey(K key) throws IllegalArgumentException
	{
		if (!parameter.containsKey(key))
			throw new IllegalArgumentException(name + " must contain " + key + ". Was: " + parameter);
		return this;
	}
}
