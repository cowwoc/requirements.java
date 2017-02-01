/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@code ArrayVerifier}.
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
	private final CollectionVerifier<E> asCollection;

	/**
	 * Creates new ArrayVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public ArrayVerifierImpl(ApplicationScope scope, E[] actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionVerifierImpl<>(scope, asList(actual), name,
			Pluralizer.ELEMENT, config);
	}

	@Override
	public ArrayVerifier<E> isEqualTo(E[] value)
	{
		asCollection.isEqualTo(asList(value));
		return this;
	}

	@Override
	public ArrayVerifier<E> isEqualTo(E[] value, String name)
	{
		asCollection.isEqualTo(asList(value), name);
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(E[] value)
	{
		asCollection.isNotEqualTo(asList(value));
		return this;
	}

	@Override
	public ArrayVerifier<E> isNotEqualTo(E[] value, String name)
	{
		asCollection.isNotEqualTo(asList(value), name);
		return this;
	}

	@Override
	public ArrayVerifier<E> isIn(Collection<E[]> collection)
	{
		scope.getInternalVerifier().requireThat(collection, "collection").isNotNull();
		if (collection.contains(actual))
			return this;

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", Arrays.toString(actual)).
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
	public ArrayVerifier<E> contains(E expected, String name)
	{
		asCollection.contains(expected, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> expected)
	{
		asCollection.containsExactly(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> expected, String name)
	{
		asCollection.containsExactly(expected, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> expected)
	{
		asCollection.containsAny(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> elements, String name)
	{
		asCollection.containsAny(elements, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> expected)
	{
		asCollection.containsAll(expected);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> expected, String name)
	{
		asCollection.containsAll(expected, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(E element)
	{
		asCollection.doesNotContain(element);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContain(E element, String name)
	{
		asCollection.doesNotContain(element, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(Collection<E> elements)
	{
		asCollection.doesNotContainAny(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAny(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAny(elements, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(Collection<E> elements)
	{
		asCollection.doesNotContainAll(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainAll(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAll(elements, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> doesNotContainDuplicates()
	{
		asCollection.doesNotContainDuplicates();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier length()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.length, name,
			name + ".length", Pluralizer.ELEMENT, config);
	}

	@Override
	public ArrayVerifier<E> length(Consumer<PrimitiveIntegerVerifier> verifier)
	{
		verifier.accept(length());
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, Arrays.toString(actual), name, config);
	}

	@Override
	public ArrayVerifier<E> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public CollectionVerifier<E> asCollection()
	{
		return asCollection;
	}

	@Override
	public ArrayVerifier<E> asCollection(Consumer<CollectionVerifier<E>> consumer)
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
