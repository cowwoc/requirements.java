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
 * Default implementation of MapPreconditions.keySet().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapKeySetPreconditionsImpl<K, V>
	extends AbstractObjectPreconditions<CollectionPreconditions<K, Set<K>>, Set<K>>
	implements CollectionPreconditions<K, Set<K>>
{
	private final Map<K, V> map;

	/**
	 * Creates new MapKeySetPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapKeySetPreconditionsImpl(Map<K, V> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.keySet(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public CollectionPreconditions<K, Set<K>> isEmpty()
		throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty.\n" +
				"Actual: %s", name, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> isNotEmpty()
		throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> contains(K element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> contains(K element, String name)
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
	public CollectionPreconditions<K, Set<K>> containsAny(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any key in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	/**
	 * @param elements a collection of elements
	 * @return true if {@code parameter} contains any of {@code elements}
	 */
	private boolean parameterContainsAny(Collection<K> elements)
	{
		for (K element: elements)
			if (parameter.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionPreconditions<K, Set<K>> containsAny(Collection<K> elements,
		String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any key in %s\n" +
				"Keys  : %s\n" +
				"Actual: %s", this.name, name, elements, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> containsAll(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<K> missing = elementsMinusParameter(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all keys in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, map, missing));
	}

	/**
	 * @param elements a collection of elements
	 * @return {@code elements} excluding the contents of {@code parameter}
	 */
	private Set<K> elementsMinusParameter(Collection<K> elements)
	{
		Set<K> missing = new HashSet<>(elements);
		for (Iterator<K> i = missing.iterator(); i.hasNext();)
		{
			if (parameter.contains(i.next()))
				i.remove();
		}
		return missing;
	}

	@Override
	public CollectionPreconditions<K, Set<K>> containsAll(Collection<K> elements,
		String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<K> missing = elementsMinusParameter(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all keys in %s\n" +
				"Keys   : %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, elements, map, missing));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContain(K element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContain(K element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Key   : %s\n",
				"Actual: %s", this.name, name, element, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContainAny(
		Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<K> unwanted = parameterIntersectWith(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any key in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, map, unwanted));
	}

	/**
	 * @param elements a collection of elements
	 * @return elements found both in {@code parameter} and {@code elements}
	 */
	private Set<K> parameterIntersectWith(Collection<K> elements)
	{
		Set<K> result = new HashSet<>(elements);
		for (Iterator<K> i = result.iterator(); i.hasNext();)
		{
			if (!parameter.contains(i.next()))
				i.remove();
		}
		return result;
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContainAny(
		Collection<K> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<K> unwanted = parameterIntersectWith(elements);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any key in %s\n" +
				"Keys    : %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, map, unwanted));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContainAll(
		Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all keys in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	@Override
	public CollectionPreconditions<K, Set<K>> doesNotContainAll(
		Collection<K> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all keys in %s\n" +
				"Keys  : %s\n" +
				"Actual: %s", this.name, name, elements, map));
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return new CollectionSizePreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected CollectionPreconditions<K, Set<K>> valueOf(Set<K> parameter,
		String name, Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		if (exceptionOverride.equals(this.exceptionOverride))
			return this;
		return new MapKeySetPreconditionsImpl<>(map, name, exceptionOverride);
	}
}
