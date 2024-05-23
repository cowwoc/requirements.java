/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

/**
 * Validates the state of a {@code Map} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 * @param <T> the type of the map
 */
public interface MapValidator<K, V, T extends Map<K, V>> extends
	Validator<MapValidator<K, V, T>>,
	ObjectPart<MapValidator<K, V, T>, T>
{
	/**
	 * Returns a validator for the value's {@link Map#keySet() keys}.
	 *
	 * @return a validator for the value's {@link Map#keySet() keys}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<K, Set<K>> keySet();

	/**
	 * Returns a validator for the value's {@link Map#values() values}.
	 *
	 * @return a validator for the value's {@link Map#values() values}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<V, Collection<V>> values();

	/**
	 * Returns a validator for the value's {@link Map#entrySet() entries}.
	 *
	 * @return a validator for the value's {@link Map#entrySet() entries}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<Entry<K, V>, Set<Entry<K, V>>> entrySet();

	/**
	 * Ensures that the value is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not empty
	 */
	MapValidator<K, V, T> isEmpty();

	/**
	 * Ensures that the value is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is empty
	 */
	MapValidator<K, V, T> isNotEmpty();

	/**
	 * Returns a validator for the map's {@link Map#size() size}.
	 *
	 * @return a validator for the map's {@link Map#size() size}
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator size();
}