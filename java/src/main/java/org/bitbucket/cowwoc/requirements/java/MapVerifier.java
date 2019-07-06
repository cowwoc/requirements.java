/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

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
public interface MapVerifier<K, V> extends ExtensibleObjectVerifier<MapVerifier<K, V>, Map<K, V>>
{
	/**
	 * Returns a verifier for the {@link Map#keySet()}.
	 *
	 * @return a verifier for the {@link Map#keySet()}
	 */
	CollectionVerifier<Set<K>, K> keySet();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#keySet()}
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer);

	/**
	 * Returns a verifier for the {@link Map#values()}.
	 *
	 * @return a verifier for the {@link Map#values()}
	 */
	CollectionVerifier<Collection<V>, V> values();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#values()}
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer);

	/**
	 * Returns a verifier for the {@link Map#entrySet()}.
	 *
	 * @return a verifier for the {@link Map#entrySet()}
	 */
	CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>> entrySet();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#entrySet()}
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapVerifier<K, V> entrySet(Consumer<CollectionVerifier<Set<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MapVerifier<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MapVerifier<K, V> isNotEmpty();

	/**
	 * Returns a verifier for {@link Map#size()}.
	 *
	 * @return a verifier for {@link Map#size()}
	 */
	SizeVerifier size();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#size()}
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapVerifier<K, V> size(Consumer<SizeVerifier> consumer);
}
