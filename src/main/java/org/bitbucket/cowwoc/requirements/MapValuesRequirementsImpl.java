/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of MapRequirements.values().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapValuesRequirementsImpl<K, V> implements CollectionRequirements<V>
{
	private final Map<K, V> map;
	private final Collection<V> parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<V> asCollection;

	/**
	 * Creates new MapValuesRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapValuesRequirementsImpl(Map<K, V> parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.map = parameter;
		this.parameter = parameter.values();
		this.name = name + ".values()";
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param map       a {@code Map}
	 * @param parameter the map values
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 */
	private MapValuesRequirementsImpl(Map<K, V> map, Collection<V> parameter, String name,
		Configuration config)
	{
		assert (map != null);
		assert (parameter != null);
		assert (name != null);
		assert (config != null);
		this.map = map;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(this.parameter, name, config);
	}

	@Override
	public CollectionRequirements<V> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapValuesRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<V> withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapValuesRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<V> isEqualTo(Collection<V> value)
		throws IllegalArgumentException
	{
		asCollection.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<V> isEqualTo(Collection<V> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotEqualTo(Collection<V> value)
		throws IllegalArgumentException
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotEqualTo(Collection<V> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<V> isIn(Collection<Collection<V>> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isIn(collection);
		return this;
	}

	@Override
	public CollectionRequirements<V> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNull() throws IllegalArgumentException
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotNull() throws NullPointerException
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<V> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty.", name),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<V> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<V> contains(V element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<V> contains(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s", this.name, name),
			"Actual", map,
			"Missing", element);
	}

	@Override
	public CollectionRequirements<V> containsExactly(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<V> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s values must contain exactly: %s", name, elements),
			"Actual", parameter,
			"Missing", missing,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<V> containsExactly(Collection<V> elements,
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s values must contain exactly %s", this.name, name),
			"Actual", parameter,
			"Expected", elements,
			"Missing", missing,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<V> containsAny(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any value in: %s", name, elements),
			"Actual", map);
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
	public CollectionRequirements<V> containsAny(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any value in %s", this.name, name),
			"Actual", map,
			"Expected", elements);
	}

	@Override
	public CollectionRequirements<V> containsAll(Collection<V> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all values in: %s", name, elements),
			"Actual", map,
			"Missing", missing);
	}

	@Override
	public CollectionRequirements<V> containsAll(Collection<V> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all values in %s", this.name, name),
			"Actual", map,
			"Expected", elements,
			"Missing", missing);
	}

	@Override
	public CollectionRequirements<V> doesNotContain(V element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain: %s", name, element),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<V> doesNotContain(V element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain %s", this.name, name),
			"Actual", map,
			"Expected", element);
	}

	@Override
	public CollectionRequirements<V> doesNotContainAny(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any value in: %s", name, elements),
			"Actual", map,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<V> doesNotContainAny(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any value in %s", this.name, name),
			"Actual", map,
			"Expected", elements,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<V> doesNotContainAll(
		Collection<V> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all values in: %s", name, elements),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<V> doesNotContainAll(
		Collection<V> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all values in %s", this.name, name),
			"Actual", map,
			"Expected", elements);
	}

	@Override
	public CollectionRequirements<V> doesNotContainDuplicates()
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain duplicate values", name),
			"Actual", parameter,
			"Duplicates", duplicates);
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(parameter, name, config);
	}

	@Override
	public CollectionRequirements<V> isolate(Consumer<CollectionRequirements<V>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
