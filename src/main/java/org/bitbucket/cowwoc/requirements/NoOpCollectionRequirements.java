/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of CollectionRequirements that does nothing.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class NoOpCollectionRequirements<E, T extends Collection<E>>
	implements CollectionRequirements<E, T>
{
	@Override
	public CollectionRequirements<E, T> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isNull()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> isNotNull()
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainDuplicates() throws IllegalArgumentException
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return NoOpCollectionSizeRequirements.INSTANCE;
	}

	@Override
	public CollectionRequirements<E, T> isolate(Consumer<CollectionRequirements<E, T>> consumer)
	{
		return this;
	}
}
