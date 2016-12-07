/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;

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
	private final SingletonScope scope;
	private final E[] actual;
	private final String name;
	private final Configuration config;
	private final CollectionVerifier<E> asCollection;

	/**
	 * Creates new ArrayVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public ArrayVerifierImpl(SingletonScope scope, E[] actual, String name, Configuration config)
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
	public ArrayVerifier<E> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ArrayVerifierImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public ArrayVerifier<E> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ArrayVerifierImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public ArrayVerifier<E> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ArrayVerifierImpl<>(scope, actual, name, newConfig);
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

		throw config.exceptionBuilder(IllegalArgumentException.class,
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
	public ArrayVerifier<E> contains(E element)
	{
		asCollection.contains(element);
		return this;
	}

	@Override
	public ArrayVerifier<E> contains(E element, String name)
	{
		asCollection.contains(element, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> elements)
	{
		asCollection.containsExactly(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsExactly(Collection<E> elements, String name)
	{
		asCollection.containsExactly(elements, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> elements)
	{
		asCollection.containsAny(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAny(Collection<E> elements, String name)
	{
		asCollection.containsAny(elements, name);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> elements)
	{
		asCollection.containsAll(elements);
		return this;
	}

	@Override
	public ArrayVerifier<E> containsAll(Collection<E> elements, String name)
	{
		asCollection.containsAll(elements, name);
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
	public ContainerSizeVerifier length()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.length, name,
			name + ".length", Pluralizer.ELEMENT, config);
	}

	@Override
	public ArrayVerifier<E> isolate(Consumer<ArrayVerifier<E>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
