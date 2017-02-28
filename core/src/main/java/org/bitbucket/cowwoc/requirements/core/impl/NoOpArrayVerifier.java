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
 * An implementation of {@code ArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public final class NoOpArrayVerifier<E>
	extends NoOpObjectCapabilities<ArrayVerifier<E>, E[]>
	implements ArrayVerifier<E>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected ArrayVerifier<E> getThis()
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
	public PrimitiveIntegerVerifier length()
	{
		return new NoOpPrimitiveIntegerVerifier(config);
	}

	@Override
	public ArrayVerifier<E> length(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> asCollection()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public ArrayVerifier<E> asCollection(Consumer<CollectionVerifier<E>> consumer)
	{
		return this;
	}
}
