/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code CollectionRequirements}.
 * <p>
 * @param <E> the type of element in the collection
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
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionRequirementsImpl(Collection<E> parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty.", name),
			"Actual", parameter);
	}

	@Override
	public CollectionRequirements<E> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<E> contains(E element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element),
			"Actual", parameter);
	}

	@Override
	public CollectionRequirements<E> contains(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, parameter, element));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s elements must contain exactly: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", name, elements, parameter, missing, unwanted));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s elements must contain exactly %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<E> containsAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any element in: %s\n" +
				"Actual: %s", name, elements, parameter));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any element in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, parameter));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all elements in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, parameter, missing));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all elements in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s", this.name, name, elements, parameter, missing));
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, parameter));
	}

	@Override
	public CollectionRequirements<E> doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, element, parameter));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any element in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, parameter, unwanted));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any element in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, unwanted));
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all of: %s\n" +
				"Actual: %s", name, elements, parameter));
	}

	@Override
	public CollectionRequirements<E> doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all elements in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, parameter));
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain duplicate elements\n" +
				"Actual: %s\n" +
				"Duplicates: %s", name, parameter, duplicates));
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
