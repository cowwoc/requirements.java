/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Map;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.ObjectVerifierSpi;

/**
 * Verifies a {@link Map} parameter.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
public interface MapVerifier<K, V>
	extends ObjectVerifierSpi<MapVerifier<K, V>, Map<K, V>>,
	Isolatable<MapVerifier<K, V>>
{
	/**
	 * @return verifier for {@link Map#keySet()}
	 */
	CollectionVerifier<K> keySet();

	/**
	 * @return verifier for {@link Map#values()}
	 */
	CollectionVerifier<V> values();

	/**
	 * @return verifier for {@link Map#entrySet()}
	 */
	CollectionVerifier<Entry<K, V>> entrySet();

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
	 * @return verifier for {@link Map#size()}
	 */
	ContainerSizeVerifier size();
}
