/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * An implementation of CollectionPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpCollectionPreconditions implements CollectionPreconditions<Object, Collection<Object>>
{
	INSTANCE;

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isNotEmpty()
	{
		return this;
	}

	@Override
	public NoOpCollectionPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isEqualTo(Collection<Object> value)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isEqualTo(Collection<Object> value,
		String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isNotEqualTo(Collection<Object> value)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isNotEqualTo(Collection<Object> value,
		String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isNull()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> isNotNull()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> containsAny(Collection<Object> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> containsAny(Collection<Object> elements,
		String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> containsAll(Collection<Object> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> containsAll(Collection<Object> elements,
		String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContain(Object element)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContain(Object element,
		String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContainAny(
		Collection<Object> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContainAny(
		Collection<Object> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContainAll(
		Collection<Object> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object, Collection<Object>> doesNotContainAll(
		Collection<Object> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return NoOpCollectionSizePreconditions.INSTANCE;
	}
}
