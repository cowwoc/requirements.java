/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Validates the state of a Multimap, throwing an exception on the first failure.
 *
 * @param <K> the type of keys in the {@code Multimap}
 * @param <V> the type of values in the {@code Multimap}
 * @param <T> the type of the {@code Multimap}
 */
public interface MultimapValidator<K, V, T extends Multimap<K, V>> extends
	Validator<MultimapValidator<K, V, T>>,
	ObjectPart<MultimapValidator<K, V, T>, T>
{
	/**
	 * Ensures that the Multimap is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the Multimap is not empty
	 */
	MultimapValidator<K, V, T> isEmpty();

	/**
	 * Ensures that the Multimap is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the Multimap is empty
	 */
	MultimapValidator<K, V, T> isNotEmpty();

	/**
	 * Returns a validator over the Multimap's {@link Multimap#keySet() keys}.
	 *
	 * @return a validator over the Multimap's {@link Multimap#keySet() keys}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<K, Set<K>> keySet();

	/**
	 * Returns a validator over the Multimap's {@link Multimap#values() values}.
	 *
	 * @return a validator over the Multimap's {@link Multimap#values() values}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<V, Collection<V>> values();

	/**
	 * Returns a validator over the Multimap's {@link Multimap#entries() entries}.
	 *
	 * @return a validator over the Multimap's {@link Multimap#entries() entries}
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<Entry<K, V>, Collection<Entry<K, V>>> entries();

	/**
	 * Returns a validator over the Multimap's {@link Multimap#size() size}.
	 *
	 * @return a validator over the Multimap's {@link Multimap#size() size}.
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator size();
}