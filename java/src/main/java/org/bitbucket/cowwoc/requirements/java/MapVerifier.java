/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ObjectCapabilities;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link Map}.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public interface MapVerifier<K, V> extends ObjectCapabilities<MapVerifier<K, V>, Map<K, V>>
{
	/**
	 * @return a verifier for the {@link Map#keySet()}
	 */
	CollectionVerifier<Set<K>, K> keySet();

	/**
	 * @param consumer verifies the {@link Map#keySet()}
	 * @return this;
	 */
	MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer);

	/**
	 * @return a verifier for the {@link Map#values()}
	 */
	CollectionVerifier<Collection<V>, V> values();

	/**
	 * @param consumer verifies the {@link Map#values()}
	 * @return this
	 */
	MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer);

	/**
	 * @return a verifier for the {@link Map#entrySet()}
	 */
	CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet();

	/**
	 * @param consumer verifies the {@link Map#entrySet()}
	 * @return this
	 */
	MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MapVerifier<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MapVerifier<K, V> isNotEmpty();

	/**
	 * @return a verifier for {@link Map#size()}
	 */
	PrimitiveNumberVerifier<Integer> size();

	/**
	 * @param consumer verifies the {@link Map#size()}
	 * @return this
	 */
	MapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer);
}
