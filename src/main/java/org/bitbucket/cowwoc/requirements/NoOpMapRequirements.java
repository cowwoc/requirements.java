/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An implementation of MapRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpMapRequirements implements MapRequirements<Object, Object>
{
	// Cannot be implemented as an enum because of a collision with Enum.values() method
	@SuppressWarnings("PackageVisibleField")
	public static NoOpMapRequirements INSTANCE = new NoOpMapRequirements();

	@Override
	public CollectionRequirements<Object, Set<Object>> keySet()
	{
		return new NoOpCollectionRequirements<>();
	}

	@Override
	public CollectionRequirements<Object, Collection<Object>> values()
	{
		return new NoOpCollectionRequirements<>();
	}

	@Override
	public CollectionRequirements<Entry<Object, Object>, Collection<Entry<Object, Object>>> entrySet()
	{
		return new NoOpCollectionRequirements<>();
	}

	@Override
	public MapRequirements<Object, Object> isEmpty()
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public NoOpMapRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isEqualTo(Map<Object, Object> value)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isEqualTo(Map<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isNotEqualTo(Map<Object, Object> value)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isNotEqualTo(Map<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isNull()
	{
		return this;
	}

	@Override
	public MapRequirements<Object, Object> isNotNull()
	{
		return this;
	}

	@Override
	public MapSizeRequirements size()
	{
		return NoOpMapSizeRequirements.INSTANCE;
	}

	@Override
	public MapRequirements<Object, Object> isolate(
		Consumer<MapRequirements<Object, Object>> consumer)
	{
		return this;
	}
}
