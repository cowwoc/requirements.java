/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code CollectionRequirements}.
 * <p>
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
class CollectionRequirementsImpl<E> implements CollectionRequirements<E>
{
	private final Collection<E> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Collection<E>> asObject;

	/**
	 * Creates new CollectionRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	CollectionRequirementsImpl(Collection<E> parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public CollectionRequirements<E> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<E> addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<E> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<E> isEqualTo(Collection<E> value)
		throws IllegalArgumentException
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<E> isEqualTo(Collection<E> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotEqualTo(Collection<E> value)
		throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotEqualTo(Collection<E> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<E> isIn(Collection<Collection<E>> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public CollectionRequirements<E> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<E> isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<E> isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<E> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public CollectionRequirements<E> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionRequirements<E> contains(E element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain: %s.", name, element)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public CollectionRequirements<E> contains(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Missing", element).
			build();
	}

	@Override
	public CollectionRequirements<E> containsExactly(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<E> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain exactly: %s.", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<E> containsExactly(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<E> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain exactly the same elements as %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<E> containsAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any element in: %s.", name, elements)).
			addContext("Actual", parameter).
			build();
	}

	/**
	 * @param elements a collection of elements
	 * @return true if {@code parameter} contains any of {@code elements}
	 */
	private boolean parameterContainsAny(Collection<E> elements)
	{
		for (E element: elements)
			if (parameter.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionRequirements<E> containsAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any element in %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Missing", elements).
			build();
	}

	@Override
	public CollectionRequirements<E> containsAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all elements in: %s.", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionRequirements<E> containsAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all elements in %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Required", elements).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain: %s.", name, element)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Unwanted", element).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContainAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any element in: %s.", name, elements)).
			addContext("Actual", parameter).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContainAny(Collection<E> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any element in %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Elements", elements).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all of: %s.", name, elements)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all elements in %s.", this.name, name)).
			addContext("Actual", parameter).
			addContext("Unwanted", elements).
			build();
	}

	@Override
	public CollectionRequirements<E> doesNotContainDuplicates() throws IllegalArgumentException
	{
		if (parameter instanceof Set)
			return this;
		int size = parameter.size();
		Set<E> unique = new HashSet<>(size);
		Set<E> duplicates = new HashSet<>(size);
		for (E element: parameter)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		if (duplicates.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain duplicate elements.", name)).
			addContext("Actual", parameter).
			addContext("Duplicates", duplicates).
			build();
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(parameter, name, config);
	}

	@Override
	public CollectionRequirements<E> isolate(Consumer<CollectionRequirements<E>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
