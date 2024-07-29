/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

/**
 * Validates the state of a {@code Map}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
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