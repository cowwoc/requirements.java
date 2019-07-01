/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

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
	 * @return a validator over {@link Multimap#keySet()}
	 */
	CollectionValidator<Set<K>, K> keySet();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#keySet()}
	 * @return this
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer);

	/**
	 * @return a validator over {@link Multimap#values()}
	 */
	CollectionValidator<Collection<V>, V> values();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#values()}
	 * @return this
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer);

	/**
	 * @return a validator over {@link Multimap#entries()}
	 */
	CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the {@link Multimap#entries()}
	 * @return this
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> entries(
		Consumer<CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>>> consumer);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not empty
	 */
	MultimapValidator<K, V> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is empty
	 */
	MultimapValidator<K, V> isNotEmpty();

	/**
	 * @return a validator over {@link Multimap#size()}
	 */
	SizeValidator size();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the multimap's size
	 * @return this
	 */
	@SuppressWarnings("LongLine")
	MultimapValidator<K, V> size(Consumer<SizeValidator> consumer);
}