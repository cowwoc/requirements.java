/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a Multimap.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public interface MultimapVerifier<K, V>
	extends ExtensibleObjectVerifier<MultimapVerifier<K, V>, Multimap<K, V>>
{
	/**
	 * @return a verifier over {@link Multimap#keySet()}
	 */
	CollectionVerifier<Set<K>, K> keySet();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Multimap#keySet()}
	 * @return the updated verifier
	 */
	@SuppressWarnings("LongLine")
	MultimapVerifier<K, V> keySet(Consumer<CollectionVerifier<Set<K>, K>> consumer);

	/**
	 * @return a verifier over {@link Multimap#values()}
	 */
	CollectionVerifier<Collection<V>, V> values();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Multimap#values()}
	 * @return the updated verifier
	 */
	@SuppressWarnings("LongLine")
	MultimapVerifier<K, V> values(Consumer<CollectionVerifier<Collection<V>, V>> consumer);

	/**
	 * @return a verifier over {@link Multimap#entries()}
	 */
	CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>> entries();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Multimap#entries()}
	 * @return the updated verifier
	 */
	@SuppressWarnings("LongLine")
	MultimapVerifier<K, V> entries(Consumer<CollectionVerifier<Collection<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MultimapVerifier<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MultimapVerifier<K, V> isNotEmpty();

	/**
	 * @return a verifier over {@link Multimap#size()}
	 */
	SizeVerifier size();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the multimap's size
	 * @return the updated verifier
	 */
	@SuppressWarnings("LongLine")
	MultimapVerifier<K, V> size(Consumer<SizeVerifier> consumer);
}
