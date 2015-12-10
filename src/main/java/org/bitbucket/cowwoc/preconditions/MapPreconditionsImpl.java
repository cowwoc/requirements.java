/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of MapPreconditions.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapPreconditionsImpl<K, V> extends AbstractObjectPreconditions<MapPreconditions<K, V>, Map<K, V>>
	implements MapPreconditions<K, V>
{
	/**
	 * Creates new MapPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapPreconditionsImpl(Map<K, V> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public MapPreconditions<K, V> containsKey(K key) throws IllegalArgumentException
	{
		if (parameter.containsKey(key))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain key: %s.\n" +
				"Actual: %s", name, key, parameter));
	}

	@Override
	public MapPreconditions<K, V> doesNotContainKey(K key) throws IllegalArgumentException
	{
		if (!parameter.containsKey(key))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain key: %s.\n" +
				"Actual: %s", name, key, parameter));
	}

	@Override
	public MapPreconditions<K, V> containsValue(V value) throws IllegalArgumentException
	{
		if (parameter.containsValue(value))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain value: %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public MapPreconditions<K, V> doesNotContainValue(V value) throws IllegalArgumentException
	{
		if (!parameter.containsValue(value))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must not contain value: %s.\n" +
				"Actual: %s", name, value, parameter));
	}

	@Override
	public MapPreconditions<K, V> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty.\n" +
			"Actual: %s", name, parameter));
	}

	@Override
	public MapPreconditions<K, V> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	@Override
	public MapSizePreconditions size()
	{
		return new MapSizePreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected MapPreconditions<K, V> valueOf(Map<K, V> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		return new MapPreconditionsImpl<>(parameter, name, exceptionOverride);
	}
}
