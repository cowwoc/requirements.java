/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

/**
 * Verifies a {@link Multimap}.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 * @author Gili Tzabari
 */
public interface MultimapVerifier<K, V>
	extends ObjectCapabilities<MultimapVerifier<K, V>, Multimap<K, V>>
{
	/**
	 * @return a verifier over {@link Multimap#keySet()}
	 */
	CollectionVerifier<Set<K>, K> keySet();

	/**
	 * @param consumer verifies the {@link Multimap#keySet()}
	 * @return this
	 */
	MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer);

	/**
	 * @return verifier over {@link Multimap#values()}
	 */
	CollectionVerifier<Collection<V>, V> values();

	/**
	 * @param consumer verifies the {@link Multimap#values()}
	 * @return this
	 */
	MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer);

	/**
	 * @return verifier over {@link Multimap#entries()}
	 */
	CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries();

	/**
	 * @param consumer verifies the {@link Multimap#entries()}
	 * @return this
	 */
	MultimapVerifier<K, V> entries(
		Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MultimapVerifier<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MultimapVerifier<K, V> isNotEmpty();

	/**
	 * @return verifier over {@link Multimap#size()}
	 */
	PrimitiveNumberVerifier<Integer> size();

	/**
	 * @param consumer verifies the multimap's size
	 * @return this
	 */
	MultimapVerifier<K, V> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer);
}
