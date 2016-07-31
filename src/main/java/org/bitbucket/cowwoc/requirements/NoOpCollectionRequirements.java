/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of CollectionRequirements that does nothing.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class NoOpCollectionRequirements<E> implements CollectionRequirements<E>
{
	@Override
	public CollectionRequirements<E> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isNull()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotNull()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return NoOpCollectionSizeRequirements.INSTANCE;
	}

	@Override
	public CollectionRequirements<E> isolate(Consumer<CollectionRequirements<E>> consumer)
	{
		return this;
	}
}
