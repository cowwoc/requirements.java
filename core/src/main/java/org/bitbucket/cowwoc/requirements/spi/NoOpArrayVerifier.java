/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.ContainerSizeVerifier;

/**
 * An implementation of ArrayVerifier that does nothing.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public final class NoOpArrayVerifier<E> implements ArrayVerifier<E>
{
	@Override
	public ArrayVerifier<E> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isEqualTo(E[] value)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isEqualTo(E[] value, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(E[] value)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(E[] value, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isIn(Collection<E[]> collection)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isNull()
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotNull()
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> contains(Object element)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public ArrayVerifier<E> isolate(Consumer<ArrayVerifier<E>> consumer)
	{
		return this;
	}
}
