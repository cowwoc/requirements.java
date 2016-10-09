/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Sets;

/**
 * Default implementation of MapRequirements.keySet().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapKeySetRequirementsImpl<K, V> implements CollectionRequirements<K>
{
	private final Map<K, V> map;
	private final Collection<K> parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<K> asCollection;

	/**
	 * Creates new MapKeySetRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	MapKeySetRequirementsImpl(Map<K, V> parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.map = parameter;
		this.parameter = parameter.keySet();
		this.name = name + ".keySet()";
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param map       a {@code Map}
	 * @param parameter the {@code keySet()} of the map
	 * @param name      the name of the {@code keySet()}
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code map}, {@code name} or {@code config} are null; if {@code name}
	 *                        is empty
	 */
	private MapKeySetRequirementsImpl(Map<K, V> map, Collection<K> parameter, String name,
		Configuration config) throws AssertionError
	{
		assert (map != null): "map may not be null";
		assert (parameter != null): "parameter may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.map = map;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(this.parameter, name, config);
	}

	@Override
	public CollectionRequirements<K> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapKeySetRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<K> addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapKeySetRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<K> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<K> isEqualTo(Collection<K> value) throws IllegalArgumentException
	{
		asCollection.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<K> isEqualTo(Collection<K> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<K> isNotEqualTo(Collection<K> value)
		throws IllegalArgumentException
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<K> isNotEqualTo(Collection<K> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<K> isIn(Collection<Collection<K>> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isIn(collection);
		return this;
	}

	@Override
	public CollectionRequirements<K> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<K> isNull() throws IllegalArgumentException
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<K> isNotNull() throws NullPointerException
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<K> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionRequirements<K> contains(K element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> contains(K element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Missing", element).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> containsExactly(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<K> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s keys must contain exactly: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<K> containsExactly(Collection<K> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<K> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s keys must contain exactly %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<K> containsAny(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any key in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
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
	public CollectionRequirements<K> containsAny(Collection<K> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any key in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> containsAll(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all keys in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> containsAll(Collection<K> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all keys in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContain(K element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain: %s", name, element)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContain(K element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", element).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContainAny(
		Collection<K> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any key in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContainAny(
		Collection<K> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<K> elementsAsSet = Collections.asSet(elements);
		Set<K> parameterAsSet = Collections.asSet(parameter);
		Set<K> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any key in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContainAll(
		Collection<K> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all keys in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContainAll(
		Collection<K> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all keys in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<K> doesNotContainDuplicates()
		throws IllegalArgumentException
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(parameter, name, config);
	}

	@Override
	public CollectionRequirements<K> isolate(Consumer<CollectionRequirements<K>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
