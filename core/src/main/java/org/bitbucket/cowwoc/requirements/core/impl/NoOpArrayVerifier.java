/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ArrayVerifier that does nothing.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public final class NoOpArrayVerifier<E> implements ArrayVerifier<E>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpArrayVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
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
		return new NoOpContainerSizeVerifier(config);
	}

	@Override
	public ArrayVerifier<E> length(Consumer<ContainerSizeVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public ArrayVerifier<E> asString(Consumer<StringVerifier> consumer)
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

	@Override
	public Optional<E[]> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public E[] getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public ArrayVerifier<E> configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
