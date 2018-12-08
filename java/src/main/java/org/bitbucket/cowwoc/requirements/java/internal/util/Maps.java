/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Map helper functions.
 */
public final class Maps
{
	/**
	 * The class names of known unmodifiable lists.
	 * <p>
	 * See http://stackoverflow.com/a/19841066/14731 for the decision to use a List.
	 * See http://stackoverflow.com/a/4129172/14731 for the decision to look up classes.
	 */
	private static final List<Class<?>> UNMODIFIABLE_CLASS_NAMES;

	static
	{
		Map<Object, Object> emptyMap = Collections.emptyMap();
		Class<?> unmodifiableMap = Collections.unmodifiableMap(emptyMap).getClass();
		Class<?> emptyMapClass = emptyMap.getClass();

		UNMODIFIABLE_CLASS_NAMES = Arrays.asList(unmodifiableMap, emptyMapClass);
	}

	/**
	 * The same as {@link Collections#unmodifiableMap(Map)} except that the input map
	 * is returned unmodified if it is already unmodifiable.
	 *
	 * @param <K> the type of keys in the map
	 * @param <V> the type of values in the map
	 * @param map the map for which an unmodifiable view is to be returned
	 * @return {@code map} if the map is already unmodifiable; otherwise, a new unmodifiable view
	 *         of the specified map
	 */
	public static <K, V> Map<K, V> unmodifiable(Map<K, V> map)
	{
		if (isUnmodifiable(map))
			return map;
		return Collections.unmodifiableMap(map);
	}

	/**
	 * @param map a map
	 * @return {@code true} if the map is unmodifiable; {@code false} if the map *may* be modifiable
	 */
	public static boolean isUnmodifiable(Map<?, ?> map)
	{
		return UNMODIFIABLE_CLASS_NAMES.contains(map.getClass());
	}

	/**
	 * Prevent construction.
	 */
	private Maps()
	{
	}
}
