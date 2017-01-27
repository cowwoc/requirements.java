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
 * An implementation of CollectionVerifier that does nothing.
 *
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public final class NoOpCollectionVerifier<E> implements CollectionVerifier<E>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpCollectionVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
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
	public CollectionVerifier<E> isEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value, String name)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isIn(Collection<Collection<E>> collection)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNull()
	{
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotNull()
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
	public ContainerSizeVerifier size()
	{
		return new NoOpContainerSizeVerifier(config);
	}

	@Override
	public CollectionVerifier<E> size(Consumer<ContainerSizeVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public CollectionVerifier<E> asString(Consumer<StringVerifier> consumer)
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

	@Override
	public Optional<Collection<E>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Collection<E> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public CollectionVerifier<E> configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
