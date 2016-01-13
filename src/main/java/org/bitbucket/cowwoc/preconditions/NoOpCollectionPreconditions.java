/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of CollectionPreconditions that does nothing.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class NoOpCollectionPreconditions<E, T extends Collection<E>>
	implements CollectionPreconditions<E, T>
{
	@Override
	public CollectionPreconditions<E, T> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isNull()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> isNotNull()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainDuplicates() throws IllegalArgumentException
	{
		return this;
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return NoOpCollectionSizePreconditions.INSTANCE;
	}

	@Override
	public CollectionPreconditions<E, T> isolate(Consumer<CollectionPreconditions<E, T>> consumer)
	{
		return this;
	}
}
