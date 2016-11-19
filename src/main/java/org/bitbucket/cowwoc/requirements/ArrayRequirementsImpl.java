/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code ArrayRequirements}.
 * <p>
 * @param <E> the type of elementss in the array
 * @author Gili Tzabari
 */
class ArrayRequirementsImpl<E> implements ArrayRequirements<E>
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
	private final E[] parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<E> asCollection;

	/**
	 * Creates new ArrayRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	ArrayRequirementsImpl(E[] parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(asList(parameter), name, config);
	}

	@Override
	public ArrayRequirements<E> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ArrayRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ArrayRequirements<E> addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		if (newConfig == config)
			return this;
		return new ArrayRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ArrayRequirements<E> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ArrayRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ArrayRequirements<E> isEqualTo(E[] value)
		throws IllegalArgumentException
	{
		asCollection.isEqualTo(asList(value));
		return this;
	}

	@Override
	public ArrayRequirements<E> isEqualTo(E[] value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isEqualTo(asList(value), name);
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEqualTo(E[] value)
		throws IllegalArgumentException
	{
		asCollection.isNotEqualTo(asList(value));
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEqualTo(E[] value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isNotEqualTo(asList(value), name);
		return this;
	}

	@Override
	public ArrayRequirements<E> isIn(Collection<E[]> collection)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(collection, "collection").isNotNull();
		if (collection.contains(parameter))
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public ArrayRequirements<E> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public ArrayRequirements<E> isNull() throws IllegalArgumentException
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotNull() throws NullPointerException
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public ArrayRequirements<E> isEmpty() throws IllegalArgumentException
	{
		asCollection.isEmpty();
		return this;
	}

	@Override
	public ArrayRequirements<E> isNotEmpty() throws IllegalArgumentException
	{
		asCollection.isNotEmpty();
		return this;
	}

	@Override
	public ArrayRequirements<E> contains(E element) throws IllegalArgumentException
	{
		asCollection.contains(element);
		return this;
	}

	@Override
	public ArrayRequirements<E> contains(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.contains(element, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsExactly(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsExactly(elements);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsExactly(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsExactly(elements, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsAny(elements);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsAny(elements, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsAll(elements);
		return this;
	}

	@Override
	public ArrayRequirements<E> containsAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.containsAll(elements, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContain(E element) throws IllegalArgumentException
	{
		asCollection.doesNotContain(element);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.doesNotContain(element, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.doesNotContainAny(elements);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.doesNotContainAny(elements, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.doesNotContainAll(elements);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.doesNotContainAll(elements, name);
		return this;
	}

	@Override
	public ArrayRequirements<E> doesNotContainDuplicates() throws IllegalArgumentException
	{
		asCollection.doesNotContainDuplicates();
		return this;
	}

	@Override
	public ArrayLengthRequirements length()
	{
		return new ArrayLengthRequirementsImpl(parameter, name, config);
	}

	@Override
	public ArrayRequirements<E> isolate(Consumer<ArrayRequirements<E>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
