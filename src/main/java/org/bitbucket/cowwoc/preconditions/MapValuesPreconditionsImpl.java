/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of MapPreconditions.values().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapValuesPreconditionsImpl<K, V>
	extends AbstractObjectPreconditions<CollectionPreconditions<V, Collection<V>>, Collection<V>>
	implements CollectionPreconditions<V, Collection<V>>
{
	private final Map<K, V> map;

	/**
	 * Creates new MapValuesPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapValuesPreconditionsImpl(Map<K, V> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride) throws NullPointerException,
		IllegalArgumentException
	{
		super(parameter.values(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty.\n" +
				"Actual: %s", name, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> contains(V element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> contains(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, map, element));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> containsAny(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
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
	public CollectionPreconditions<V, Collection<V>> containsAny(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any value in %s\n" +
				"Values: %s\n" +
				"Actual: %s", this.name, name, elements, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> containsAll(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<V> missing = elementsMinusParameter(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all values in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, map, missing));
	}

	/**
	 * @param elements a collection of elements
	 * @return {@code elements} excluding the contents of {@code parameter}
	 */
	private Set<V> elementsMinusParameter(Collection<V> elements)
	{
		Set<V> missing = new HashSet<>(elements);
		for (Iterator<V> i = missing.iterator(); i.hasNext();)
		{
			if (parameter.contains(i.next()))
				i.remove();
		}
		return missing;
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> containsAll(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<V> missing = elementsMinusParameter(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all values in %s\n" +
				"Values : %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, elements, map, missing));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContain(V element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContain(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Value : %s\n",
				"Actual: %s", this.name, name, element, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContainAny(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> unwanted = parameterIntersectWith(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any value in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, map, unwanted));
	}

	/**
	 * @param elements a collection of elements
	 * @return elements found both in {@code parameter} and {@code elements}
	 */
	private Set<V> parameterIntersectWith(Collection<V> elements)
	{
		Set<V> result = new HashSet<>(elements);
		for (Iterator<V> i = result.iterator(); i.hasNext();)
		{
			if (!parameter.contains(i.next()))
				i.remove();
		}
		return result;
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContainAny(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> unwanted = parameterIntersectWith(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any value in %s\n" +
				"Values  : %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, map, unwanted));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContainAll(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all values in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContainAll(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all values in %s\n" +
				"Values: %s\n" +
				"Actual: %s", this.name, name, elements, map));
	}

	@Override
	public CollectionPreconditions<V, Collection<V>> doesNotContainDuplicates()
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
	public CollectionSizePreconditions size()
	{
		return new CollectionSizePreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected CollectionPreconditions<V, Collection<V>> valueOf(Collection<V> parameter,
		String name, Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		if (exceptionOverride.equals(this.exceptionOverride))
			return this;
		return new MapValuesPreconditionsImpl<>(map, name, exceptionOverride);
	}
}
