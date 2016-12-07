/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.ObjectVerifierSpi;

/**
 * Verifies a {@link Multimap} parameter.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 * @author Gili Tzabari
 */
public interface MultimapVerifier<K, V>
	extends ObjectVerifierSpi<MultimapVerifier<K, V>, Multimap<K, V>>,
	Isolatable<MultimapVerifier<K, V>>
{
	/**
	 * @return verifier over {@link Multimap#keySet()}
	 */
	CollectionVerifier<K> keySet();

	/**
	 * @return verifier over {@link Multimap#values()}
	 */
	CollectionVerifier<V> values();

	/**
	 * @return verifier over {@link Multimap#entries()}
	 */
	CollectionVerifier<Entry<K, V>> entries();

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
