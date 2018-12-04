/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ArrayCapabilities;
import org.bitbucket.cowwoc.requirements.core.capabilities.ComparableCapabilities;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Extendable implementation of {@link ComparableCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <E> the Object representation of an array element
 * @param <R> the type of the array
 */
public abstract class NoOpArrayCapabilities<S, E, R>
	extends NoOpObjectCapabilities<S, R>
	implements ArrayCapabilities<S, E, R>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpArrayCapabilities(Configuration config)
	{
		super(config);
	}

	@Override
	public S isEmpty()
	{
		return getThis();
	}

	@Override
	public S isNotEmpty()
	{
		return getThis();
	}

	@Override
	public S contains(E expected)
	{
		return getThis();
	}

	@Override
	public S contains(String name, E expected)
		throws NullPointerException, IllegalArgumentException
	{
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsExactly(String name, Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAny(String name, Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S containsAll(String name, Collection<E> expected)
	{
		return getThis();
	}

	@Override
	public S doesNotContain(E element)
	{
		return getThis();
	}

	@Override
	public S doesNotContain(String name, E element)
	{
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> element)
	{
		return getThis();
	}

	@Override
	public S doesNotContainExactly(String name, Collection<E> element)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAny(String name, Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainAll(String name, Collection<E> elements)
	{
		return getThis();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		return getThis();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> length()
	{
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public S length(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		return getThis();
	}

	@Override
	public CollectionVerifier<Collection<E>, E> asCollection()
	{
		return new NoOpCollectionVerifier<>(config);
	}

	@Override
	public S asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer)
	{
		return getThis();
	}
}
