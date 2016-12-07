/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of CollectionVerifier that does nothing.
 * <p>
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
final class NoOpCollectionVerifier<E> implements CollectionVerifier<E>
{
	@Override
	public CollectionVerifier<E> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isIn(Collection<Collection<E>> collection)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNull()
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotNull()
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier size()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public CollectionVerifier<E> isolate(Consumer<CollectionVerifier<E>> consumer)
	{
		return this;
	}
}
