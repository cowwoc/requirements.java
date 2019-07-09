/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Validates the requirements of a {@link Map}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public interface MapValidator<K, V> extends ExtensibleObjectValidator<MapValidator<K, V>, Map<K, V>>
{
	/**
	 * Returns a validator for the {@link Map#keySet()}.
	 *
	 * @return a validator for the {@link Map#keySet()}
	 */
	CollectionValidator<Set<K>, K> keySet();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#keySet()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer);

	/**
	 * Returns a validator for the {@link Map#values()}.
	 *
	 * @return a validator for the {@link Map#values()}
	 */
	CollectionValidator<Collection<V>, V> values();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#values()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer);

	/**
	 * Returns a validator for the {@link Map#entrySet()}.
	 *
	 * @return a validator for the {@link Map#entrySet()}
	 */
	CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#entrySet()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapValidator<K, V> entrySet(Consumer<CollectionValidator<Set<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 */
	MapValidator<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated validator
	 */
	MapValidator<K, V> isNotEmpty();

	/**
	 * Returns a validator for {@link Map#size()}.
	 *
	 * @return a validator for {@link Map#size()}
	 */
	SizeValidator size();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@link Map#size()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	MapValidator<K, V> size(Consumer<SizeValidator> consumer);
}
