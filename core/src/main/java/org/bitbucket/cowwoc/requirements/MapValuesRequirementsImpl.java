/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Collections;
import org.bitbucket.cowwoc.requirements.util.Sets;

/**
 * Default implementation of MapRequirements.values().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapValuesRequirementsImpl<K, V> implements CollectionRequirements<V>
{
	private final SingletonScope scope;
	private final Map<K, V> map;
	private final Collection<V> parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<V> asCollection;

	/**
	 * Creates new MapValuesRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	MapValuesRequirementsImpl(SingletonScope scope, Map<K, V> parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.map = parameter;
		this.parameter = parameter.values();
		this.name = name + ".values()";
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(scope, this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param map       a {@code Map}
	 * @param parameter the map values
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code map}, {@code name} or {@code config} are null; if {@code name}
	 *                        is empty
	 */
	private MapValuesRequirementsImpl(SingletonScope scope, Map<K, V> map, Collection<V> parameter,
		String name, Configuration config)
	{
		assert (map != null): "map may not be null";
		assert (parameter != null): "parameter may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.map = map;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(scope, this.parameter, name, config);
	}

	@Override
	public CollectionRequirements<V> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapValuesRequirementsImpl<>(scope, map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<V> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapValuesRequirementsImpl<>(scope, map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<V> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<V> isEqualTo(Collection<V> value)
	{
		asCollection.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<V> isEqualTo(Collection<V> value, String name)
	{
		asCollection.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotEqualTo(Collection<V> value)
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotEqualTo(Collection<V> value, String name)
	{
		asCollection.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<V> isIn(Collection<Collection<V>> collection)
	{
		asCollection.isIn(collection);
		return this;
	}

	@Override
	public CollectionRequirements<V> isInstanceOf(Class<?> type)
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<V> isNull()
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<V> isNotNull()
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<V> isEmpty()
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
	public CollectionRequirements<V> isNotEmpty()
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionRequirements<V> contains(V element)
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
	public CollectionRequirements<V> contains(V element, String name)
	{
		scope.getDefaultVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
	public CollectionRequirements<V> containsExactly(Collection<V> elements)
	{
		scope.getDefaultVerifier().requireThat(elements, "elements").isNotNull();
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<V> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s values must contain exactly: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<V> containsExactly(Collection<V> elements, String name)
	{
		RequirementVerifier verifier = scope.getDefaultVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<V> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s values must contain exactly %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<V> containsAny(Collection<V> elements)
	{
		scope.getDefaultVerifier().requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any value in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
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
	public CollectionRequirements<V> containsAny(Collection<V> elements, String name)
	{
		RequirementVerifier verifier = scope.getDefaultVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any value in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> containsAll(Collection<V> elements)
	{
		scope.getDefaultVerifier().requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all values in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> containsAll(Collection<V> elements, String name)
	{
		RequirementVerifier verifier = scope.getDefaultVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all values in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> doesNotContain(V element)
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
	public CollectionRequirements<V> doesNotContain(V element, String name)
	{
		scope.getDefaultVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
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
	public CollectionRequirements<V> doesNotContainAny(Collection<V> elements)
	{
		scope.getDefaultVerifier().requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any value in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> doesNotContainAny(Collection<V> elements, String name)
	{
		RequirementVerifier verifier = scope.getDefaultVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<V> elementsAsSet = Collections.asSet(elements);
		Set<V> parameterAsSet = Collections.asSet(parameter);
		Set<V> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any value in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> doesNotContainAll(Collection<V> elements)
	{
		scope.getDefaultVerifier().requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all values in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> doesNotContainAll(Collection<V> elements, String name)
	{
		RequirementVerifier verifier = scope.getDefaultVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all values in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<V> doesNotContainDuplicates()
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
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain duplicate values", name)).
			addContext("Actual", parameter).
			addContext("Duplicates", duplicates).
			build();
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(scope, parameter, name, config);
	}

	@Override
	public CollectionRequirements<V> isolate(Consumer<CollectionRequirements<V>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
