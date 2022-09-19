/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Validates the requirements of a Multimap.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public interface MultimapValidator<K, V>
	extends ExtensibleObjectValidator<MultimapValidator<K, V>, Multimap<K, V>>
{
	/**
	 * Returns a validator over {@link Multimap#keySet()}.
	 *
	 * @return a validator over {@link Multimap#keySet()}
	 */
	CollectionValidator<Set<K>, K> keySet();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#keySet()}
	 * @return the updated validator
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer);

	/**
	 * Returns a validator over {@link Multimap#values()}.
	 *
	 * @return a validator over {@link Multimap#values()}
	 */
	CollectionValidator<Collection<V>, V> values();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#values()}
	 * @return the updated validator
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer);

	/**
	 * Reutrns a validator over {@link Multimap#entries()}.
	 *
	 * @return a validator over {@link Multimap#entries()}
	 */
	CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#entries()}
	 * @return the updated validator
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> entries(Consumer<CollectionValidator<Collection<Entry<K, V>>,
		Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MultimapValidator<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated validator
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MultimapValidator<K, V> isNotEmpty();

	/**
	 * Returns a validator over {@link Multimap#size()}.
	 *
	 * @return a validator over {@link Multimap#size()}
	 */
	SizeValidator size();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the multimap's size
	 * @return the updated validator
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> size(Consumer<SizeValidator> consumer);
}
