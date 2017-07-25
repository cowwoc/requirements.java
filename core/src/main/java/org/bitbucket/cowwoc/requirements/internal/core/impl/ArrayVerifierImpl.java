/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link ArrayVerifier}.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public class ArrayVerifierImpl<E> implements ArrayVerifier<E>
{
	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a list
	 */
	private static <E> List<E> asList(E[] array)
	{
		if (array == null)
			return null;
		return Arrays.asList(array);
	}
	private final ApplicationScope scope;
	private final E[] actual;
	private final String name;
	private final Configuration config;
	private final CollectionVerifier<Collection<E>, E> asCollection;

	/**
	 * Creates new ArrayVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public ArrayVerifierImpl(ApplicationScope scope, String name, E[] actual, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionVerifierImpl<>(scope, name, asList(actual),
			Pluralizer.ELEMENT, config);
	}

	@Override
	public ArrayVerifier<E> isEqualTo(Object expected)
	{
		asCollection.isEqualTo(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> isEqualTo(String name, Object expected)
	{
		asCollection.isEqualTo(name, expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(Object value)
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(String name, Object value)
	{
		asCollection.isNotEqualTo(name, value);
		return this;
	}

	@Override
	public ArrayVerifier<E> isIn(Collection<? super E[]> collection)
	{
		scope.getInternalVerifier().requireThat("collection", collection).isNotNull();
		if (collection.contains(actual))
			return this;

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ArrayVerifier<E> isNotIn(Collection<? super E[]> collection)
	{
		scope.getInternalVerifier().requireThat("collection", collection).isNotNull();
		if (!collection.contains(actual))
			return this;

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be in %s.", this.name, collection)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ArrayVerifier<E> isInstanceOf(Class<?> type)
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public ArrayVerifier<E> isNull()
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotNull()
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public ArrayVerifier<E> isEmpty()
	{
		asCollection.isEmpty();
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEmpty()
	{
		asCollection.isNotEmpty();
		return this;
	}

	@Override
	public ArrayVerifier<E> contains(E expected)
	{
		asCollection.contains(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> contains(String name, E expected)
	{
		asCollection.contains(name, expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> expected)
	{
		asCollection.containsExactly(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(String name, Collection<E> expected)
	{
		asCollection.containsExactly(name, expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> expected)
	{
		asCollection.containsAny(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(String name, Collection<E> elements)
	{
		asCollection.containsAny(name, elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> expected)
	{
		asCollection.containsAll(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(String name, Collection<E> expected)
	{
		asCollection.containsAll(name, expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(E element)
	{
		asCollection.doesNotContain(element);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(String name, E element)
	{
		asCollection.doesNotContain(name, element);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(Collection<E> elements)
	{
		asCollection.doesNotContainAny(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(String name, Collection<E> elements)
	{
		asCollection.doesNotContainAny(name, elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(Collection<E> elements)
	{
		asCollection.doesNotContainAll(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(String name, Collection<E> elements)
	{
		asCollection.doesNotContainAll(name, elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainDuplicates()
	{
		asCollection.doesNotContainDuplicates();
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> length()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".length", actual.length,
			Pluralizer.ELEMENT, config);
	}

	@Override
	public ArrayVerifier<E> length(Consumer<PrimitiveNumberVerifier<Integer>> verifier)
	{
		verifier.accept(length());
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, config.toString(actual), name, config);
	}

	@Override
	public ArrayVerifier<E> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public CollectionVerifier<Collection<E>, E> asCollection()
	{
		return asCollection;
	}

	@Override
	public ArrayVerifier<E> asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer)
	{
		consumer.accept(asCollection());
		return this;
	}

	@Override
	public Optional<E[]> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public E[] getActual()
	{
		return actual;
	}
}
