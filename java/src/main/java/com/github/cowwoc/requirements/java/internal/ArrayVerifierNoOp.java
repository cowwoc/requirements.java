/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.ListVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * An {@code ArrayVerifier} that does nothing.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierNoOp<A, E>
	extends AbstractObjectVerifierNoOp<ArrayVerifier<A, E>, A>
	implements ArrayVerifier<A, E>
{
	private static final ArrayVerifierNoOp<?, ?> INSTANCE = new ArrayVerifierNoOp<>();

	/**
	 * @param <A> the type of the array
	 * @param <E> the type of elements in the array
	 * @return the singleton instance
	 */
	public static <A, E> ArrayVerifierNoOp<A, E> getInstance()
	{
		@SuppressWarnings("unchecked")
		ArrayVerifierNoOp<A, E> result = (ArrayVerifierNoOp<A, E>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ArrayVerifierNoOp()
	{
	}

	@Override
	protected ArrayVerifier<A, E> getThis()
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> isEmpty()
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> contains(E expected)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> contains(E expected, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsExactly(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsExactly(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsAny(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsAny(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsAll(Collection<E> expected)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> containsAll(Collection<E> expected, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainExactly(Collection<E> element)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainExactly(Collection<E> element, String name)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAny(Collection<E> element, String names)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainAll(Collection<E> element, String names)
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public ArrayVerifier<A, E> isSorted(Comparator<E> comparator)
	{
		return this;
	}

	@Override
	public SizeVerifier length()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public ArrayVerifier<A, E> length(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionVerifier<Collection<E>, E> asCollection()
	{
		return CollectionVerifierNoOp.getInstance();
	}

	@Override
	public ArrayVerifier<A, E> asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public ListVerifier<List<E>, E> asList()
	{
		return ListVerifierNoOp.getInstance();
	}

	@Override
	public ArrayVerifier<A, E> asList(Consumer<ListVerifier<List<E>, E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
