/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;

/**
 * An implementation of {@code CollectionVerifier} that does nothing.
 *
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public final class NoOpCollectionVerifier<E>
	extends NoOpObjectCapabilities<CollectionVerifier<E>, Collection<E>>
	implements CollectionVerifier<E>
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
	protected CollectionVerifier<E> getThis()
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
	public PrimitiveIntegerVerifier size()
	{
		return new NoOpPrimitiveIntegerVerifier(config);
	}

	@Override
	public CollectionVerifier<E> size(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		return this;
	}

	@Override
	public ArrayVerifier<E> asArray(Class<E> type)
	{
		return new NoOpArrayVerifier<>(config);
	}

	@Override
	public CollectionVerifier<E> asArray(Class<E> type, Consumer<ArrayVerifier<E>> consumer)
	{
		return this;
	}
}
