/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * An implementation of {@code CollectionVerifier} that does nothing.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionVerifierNoOp<C extends Collection<E>, E>
	extends AbstractObjectVerifierNoOp<CollectionVerifier<C, E>, C>
	implements CollectionVerifier<C, E>
{
	private static final CollectionVerifierNoOp<?, ?> INSTANCE = new CollectionVerifierNoOp<>();

	/**
	 * @param <C> the type of the collection
	 * @param <E> the type of elements in the collection
	 * @return the singleton instance
	 */
	public static <C extends Collection<E>, E> CollectionVerifierNoOp<C, E> getInstance()
	{
		@SuppressWarnings("unchecked")
		CollectionVerifierNoOp<C, E> result = (CollectionVerifierNoOp<C, E>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private CollectionVerifierNoOp()
	{
	}

	@Override
	protected CollectionVerifier<C, E> getThis()
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> isEmpty()
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> isNotEmpty()
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> contains(Object element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> contains(Object element, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public SizeVerifier size()
	{
		return SizeVerifierNoOp.getInstance();
	}

	@Override
	public CollectionVerifier<C, E> size(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public ArrayVerifier<E> asArray(Class<E> type)
	{
		return ArrayVerifierNoOp.getInstance();
	}

	@Override
	public CollectionVerifier<C, E> asArray(Class<E> type, Consumer<ArrayVerifier<E>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
