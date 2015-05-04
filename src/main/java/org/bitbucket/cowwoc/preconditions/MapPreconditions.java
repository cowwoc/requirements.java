/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Map;

/**
 * Verifies preconditions of a {@link Map} parameter.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public interface MapPreconditions<K, V> extends ObjectPreconditions<MapPreconditions<K, V>, Map<K, V>>
{
	/**
	 * Ensures that the parameter contains a key.
	 * <p>
	 * @param key the key that must exist
	 * @return this
	 * @throws IllegalArgumentException if the map does not contain key
	 */
	MapPreconditions<K, V> containsKey(K key) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains a value.
	 * <p>
	 * @param value the value that must exist
	 * @return this
	 * @throws IllegalArgumentException if the map does not contain value
	 */
	MapPreconditions<K, V> containsValue(V value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	MapPreconditions<K, V> isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is of the specified size.
	 * <p>
	 * @param size the size the map is expected to have
	 * @return this
	 * @throws IllegalArgumentException if parameter size does not match expectations
	 */
	MapPreconditions<K, V> sizeEquals(int size) throws IllegalArgumentException;
}
