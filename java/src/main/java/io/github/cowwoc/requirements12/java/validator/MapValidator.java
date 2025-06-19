/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.validator;

import io.github.cowwoc.requirements12.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Validates the state of a {@code Map}.
 *
 * @param <T> the type of the map
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public interface MapValidator<T extends Map<K, V>, K, V> extends
	ValidatorComponent<MapValidator<T, K, V>, T>,
	ObjectComponent<MapValidator<T, K, V>, T>
{
	/**
	 * Returns a validator for the value's {@link Map#keySet() keys}.
	 *
	 * @return a validator for the value's {@link Map#keySet() keys}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<Set<K>, K> keySet();

	/**
	 * Returns a validator for the value's {@link Map#values() values}.
	 *
	 * @return a validator for the value's {@link Map#values() values}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<Collection<V>, V> values();

	/**
	 * Returns a validator for the value's {@link Map#entrySet() entries}.
	 *
	 * @return a validator for the value's {@link Map#entrySet() entries}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet();

	/**
	 * Ensures that the value is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not empty
	 */
	MapValidator<T, K, V> isEmpty();

	/**
	 * Ensures that the value is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is empty
	 */
	MapValidator<T, K, V> isNotEmpty();

	/**
	 * Returns a validator for the map's {@link Map#size() size}.
	 *
	 * @return a validator for the map's {@link Map#size() size}
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator size();
}