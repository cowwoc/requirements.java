/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of MapPreconditions.entrySet().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapEntrySetPreconditionsImpl<K, V>
	extends AbstractObjectPreconditions<CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>>, Collection<Entry<K, V>>>
	implements CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>>
{
	private final Map<K, V> map;

	/**
	 * Creates new MapEntrySetPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	MapEntrySetPreconditionsImpl(Map<K, V> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter.entrySet(), name, exceptionOverride);
		this.map = parameter;
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> isEmpty()
		throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty.\n" +
			"Actual: %s", name, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> isNotEmpty()
		throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> contains(Entry<K, V> element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> contains(Entry<K, V> element,
		String name) throws NullPointerException, IllegalArgumentException
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
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsExactly(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s entries must contain exactly: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsExactly(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s entries must contain exactly %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsAny(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any entry in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	/**
	 * @param elements a collection of elements
	 * @return true if {@code parameter} contains any of {@code elements}
	 */
	private boolean parameterContainsAny(Collection<Entry<K, V>> elements)
	{
		for (Entry<K, V> element: elements)
			if (parameter.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsAny(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any entry in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsAll(
		Collection<Entry<K, V>> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all entries in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, map, missing));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> containsAll(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all entries in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s", this.name, name, elements, map, missing));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContain(
		Entry<K, V> element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContain(
		Entry<K, V> element, String name) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, element, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContainAny(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any entry in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, map, unwanted));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContainAny(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any entry in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, map, unwanted));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContainAll(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all entries in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContainAll(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(elements, "elements").isNotNull();
		Preconditions.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all entries in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> doesNotContainDuplicates()
		throws IllegalArgumentException
	{
		return this;
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return new CollectionSizePreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected CollectionPreconditions<Entry<K, V>, Collection<Entry<K, V>>> valueOf(
		Collection<Entry<K, V>> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		if (exceptionOverride.equals(this.exceptionOverride))
			return this;
		return new MapEntrySetPreconditionsImpl<>(map, name, exceptionOverride);
	}
}
