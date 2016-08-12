/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

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
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapKeySetRequirementsImpl(Map<K, V> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
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
	 */
	private MapKeySetRequirementsImpl(Map<K, V> map, Collection<K> parameter, String name,
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
	public CollectionRequirements<K> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapKeySetRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<K> withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapKeySetRequirementsImpl<>(map, parameter, name, newConfig);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty.", name),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<K> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<K> contains(K element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<K> contains(K element, String name)
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s keys must contain exactly: %s", name, elements),
			"Actual", parameter,
			"Missing", missing,
			"Unwanted", unwanted);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s keys must contain exactly %s", this.name, name),
			"Expected", elements,
			"Actual", parameter,
			"Missing", missing,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<K> containsAny(Collection<K> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any key in: %s", name, elements),
			"Actual", map);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any key in %s", this.name, name),
			"Expected", elements,
			"Actual", map);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all keys in: %s", name, elements),
			"Actual", map,
			"Missing", missing);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all keys in %s", this.name, name),
			"Expected", elements,
			"Actual", map,
			"Missing", missing);
	}

	@Override
	public CollectionRequirements<K> doesNotContain(K element)
		throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain: %s", name, element),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<K> doesNotContain(K element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain %s", this.name, name),
			"Expected", element,
			"Actual", map);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any key in: %s", name, elements),
			"Actual", map,
			"Unwanted", unwanted);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any key in %s", this.name, name),
			"Expected", elements,
			"Actual", map,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<K> doesNotContainAll(
		Collection<K> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all keys in: %s", name, elements),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<K> doesNotContainAll(
		Collection<K> elements, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all keys in %s", this.name, name),
			"Expected", elements,
			"Actual", map);
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
