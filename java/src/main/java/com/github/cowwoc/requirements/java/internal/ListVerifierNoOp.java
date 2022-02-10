/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.ListVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@code ListVerifier} that does nothing.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public final class ListVerifierNoOp<L extends List<E>, E>
	extends AbstractObjectVerifierNoOp<ListVerifier<L, E>, L>
	implements ListVerifier<L, E>
{
	private static final ListVerifierNoOp<?, ?> INSTANCE = new ListVerifierNoOp<>();

	/**
	 * @param <L> the type of the list
	 * @param <E> the type of elements in the list
	 * @return the singleton instance
	 */
	public static <L extends List<E>, E> ListVerifierNoOp<L, E> getInstance()
	{
		@SuppressWarnings("unchecked")
		ListVerifierNoOp<L, E> result = (ListVerifierNoOp<L, E>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ListVerifierNoOp()
	{
	}

	@Override
	protected ListVerifier<L, E> getThis()
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> isEmpty()
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> contains(Object element)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public ListVerifier<L, E> isSorted(Comparator<E> comparator)
	{
		return this;
	}

	@Override
	public SizeVerifier size()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public ListVerifier<L, E> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public ArrayVerifier<E[], E> asArray(Class<E> type)
	{
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public ListVerifier<L, E> asArray(Class<E> type, Consumer<ArrayVerifier<E[], E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
