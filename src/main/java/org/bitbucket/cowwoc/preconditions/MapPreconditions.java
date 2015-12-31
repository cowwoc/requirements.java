/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Verifies preconditions of a {@link Map} parameter.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public interface MapPreconditions<K, V> extends
	ObjectPreconditions<MapPreconditions<K, V>, Map<K, V>>
{
	/**
	 * @return preconditions over {@link Map#keySet()}
	 */
	CollectionPreconditions<K, Set<K>> keySet();

	/**
	 * @return preconditions over {@link Map#values()}
	 */
	CollectionPreconditions<V, Collection<V>> values();

	/**
	 * @return preconditions over {@link Map#entrySet()}
	 */
	CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> entrySet();

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not empty
	 */
	MapPreconditions<K, V> isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	MapPreconditions<K, V> isNotEmpty() throws IllegalArgumentException;

	/**
	 * @return preconditions over {@link Map#size()}
	 */
	MapSizePreconditions size();
}
