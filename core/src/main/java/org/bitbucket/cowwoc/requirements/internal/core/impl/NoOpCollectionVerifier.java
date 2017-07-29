/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Collection;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;

/**
 * An implementation of {@link CollectionVerifier} that does nothing.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public final class NoOpCollectionVerifier<C extends Collection<E>, E>
	extends NoOpObjectCapabilities<CollectionVerifier<C, E>, C>
	implements CollectionVerifier<C, E>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpCollectionVerifier(Configuration config)
	{
		super(config);
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
	public CollectionVerifier<C, E> contains(String name, Object element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(String name, Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAny(String name, Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> containsAll(String name, Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(String name, E element)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(String name, Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(String name, Collection<E> elements)
	{
		return this;
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public CollectionVerifier<C, E> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> asArray(Class<E> type)
	{
		return new NoOpArrayVerifier<>(config);
	}

	@Override
	public CollectionVerifier<C, E> asArray(Class<E> type, Consumer<ArrayVerifier<E>> consumer)
	{
		return this;
	}
}
