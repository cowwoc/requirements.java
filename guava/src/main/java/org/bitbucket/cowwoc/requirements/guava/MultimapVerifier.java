/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies a {@link Multimap} parameter.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 * @author Gili Tzabari
 */
public interface MultimapVerifier<K, V>
	extends ObjectVerifierExtension<MultimapVerifier<K, V>, Multimap<K, V>>
{
	/**
	 * @return a verifier over {@link Multimap#keySet()}
	 */
	CollectionVerifier<K> keySet();

	/**
	 * @param consumer verifies the {@link Multimap#keySet()}
	 * @return this
	 */
	MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<K>> consumer);

	/**
	 * @return verifier over {@link Multimap#values()}
	 */
	CollectionVerifier<V> values();

	/**
	 * @param consumer verifies the {@link Multimap#values()}
	 * @return this
	 */
	MultimapVerifier<K, V> values(Consumer<CollectionVerifier<V>> consumer);

	/**
	 * @return verifier over {@link Multimap#entries()}
	 */
	CollectionVerifier<Entry<K, V>> entries();

	/**
	 * @param consumer verifies the {@link Multimap#entries()}
	 * @return this
	 */
	MultimapVerifier<K, V> entries(Consumer<CollectionVerifier<Entry<K, V>>> consumer);

	/**
	 * Ensures that the parameter is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is not empty
	 */
	MultimapVerifier<K, V> isEmpty();

	/**
	 * Ensures that the parameter is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	MultimapVerifier<K, V> isNotEmpty();

	/**
	 * @return verifier over {@link Multimap#size()}
	 */
	ContainerSizeVerifier size();
}
