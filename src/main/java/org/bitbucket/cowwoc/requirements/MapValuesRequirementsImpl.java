/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Default implementation of MapRequirements.values().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapValuesRequirementsImpl<K, V>
	extends AbstractObjectRequirements<CollectionRequirements<V, Collection<V>>, Collection<V>>
	implements CollectionRequirements<V, Collection<V>>
{
	private final Map<K, V> map;

	/**
	 * Creates new MapValuesRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapValuesRequirementsImpl(Map<K, V> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride) throws NullPointerException,
		IllegalArgumentException
	{
		super(parameter.values(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public CollectionRequirements<V, Collection<V>> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty.\n" +
				"Actual: %s", name, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> contains(V element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> contains(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, map, element));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsExactly(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<V> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s values must contain exactly: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsExactly(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<V> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s values must contain exactly %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s\n", this.name, name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsAny(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any value in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	/**
	 * @param elements a collection of elements
	 * @return true if {@code parameter} contains any of {@code elements}
	 */
	private boolean parameterContainsAny(Collection<V> elements)
	{
		for (V element: elements)
			if (parameter.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsAny(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any value in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsAll(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all values in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, map, missing));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> containsAll(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all values in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s", this.name, name, elements, map, missing));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContain(V element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContain(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, element, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContainAny(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any value in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, map, unwanted));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContainAny(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any value in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, map, unwanted));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContainAll(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all values in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContainAll(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all values in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionRequirements<V, Collection<V>> doesNotContainDuplicates()
		throws IllegalArgumentException
	{
		if (parameter instanceof Set)
			return this;
		int size = parameter.size();
		Set<V> unique = new HashSet<>(size);
		Set<V> duplicates = new HashSet<>(size);
		for (V value: parameter)
		{
			if (!unique.add(value))
				duplicates.add(value);
		}
		if (duplicates.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain duplicate value\n" +
				"Actual: %s\n" +
				"Duplicates: %s", name, parameter, duplicates));
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<V, Collection<V>> usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new MapValuesRequirementsImpl<>(map, name, exceptionOverride);
	}
}
