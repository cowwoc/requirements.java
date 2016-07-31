/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Default implementation of MapRequirements.
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapRequirementsImpl<K, V> extends AbstractObjectRequirements<MapRequirements<K, V>, Map<K, V>>
	implements MapRequirements<K, V>
{
	/**
	 * Creates new MapRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapRequirementsImpl(Map<K, V> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<K, Set<K>> keySet()
	{
		return new MapKeySetRequirementsImpl<>(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<V, Collection<V>> values()
	{
		return new MapValuesRequirementsImpl<>(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<Map.Entry<K, V>, Collection<Map.Entry<K, V>>> entrySet()
	{
		return new MapEntrySetRequirementsImpl<>(parameter, name, exceptionOverride);
	}

	@Override

	public MapRequirements<K, V> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty.\n" +
			"Actual: %s", name, parameter));
	}

	@Override
	public MapRequirements<K, V> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	@Override
	public MapSizeRequirements size()
	{
		return new MapSizeRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Override
	public MapRequirements<K, V> usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new MapRequirementsImpl<>(parameter, name, exceptionOverride);
	}
}
