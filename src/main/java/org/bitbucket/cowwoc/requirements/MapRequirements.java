/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Verifies requirements of a {@link Map} parameter.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public interface MapRequirements<K, V> extends
	ObjectRequirements<MapRequirements<K, V>, Map<K, V>>
{
	/**
	 * @return requirements over {@link Map#keySet()}
	 */
	CollectionRequirements<K, Set<K>> keySet();

	/**
	 * @return requirements over {@link Map#values()}
	 */
	CollectionRequirements<V, Collection<V>> values();

	/**
	 * @return requirements over {@link Map#entrySet()}
	 */
	CollectionRequirements<Entry<K, V>, Collection<Entry<K, V>>> entrySet();

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not empty
	 */
	MapRequirements<K, V> isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	MapRequirements<K, V> isNotEmpty() throws IllegalArgumentException;

	/**
	 * @return requirements over {@link Map#size()}
	 */
	MapSizeRequirements size();
}
