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
 * An implementation of ArrayRequirements that does nothing.
 * <p>
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
final class NoOpArrayRequirements<E> implements ArrayRequirements<E>
{
	@Override
	public ArrayRequirements<E> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isEqualTo(E[] value)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isEqualTo(E[] value, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEqualTo(E[] value)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEqualTo(E[] value, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isIn(Collection<E[]> collection)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isNull()
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotNull()
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> contains(Object element)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements length()
	{
		return NoOpArrayLengthRequirements.INSTANCE;
	}

	@Override
	public ArrayRequirements<E> isolate(Consumer<ArrayRequirements<E>> consumer)
	{
		return this;
	}
}
