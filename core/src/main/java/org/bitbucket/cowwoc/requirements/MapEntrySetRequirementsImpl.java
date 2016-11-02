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
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Collections;
import org.bitbucket.cowwoc.requirements.util.Sets;

/**
 * Default implementation of MapRequirements.entrySet().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapEntrySetRequirementsImpl<K, V> implements CollectionRequirements<Entry<K, V>>
{
	private final SingletonScope scope;
	private final Map<K, V> map;
	private final Set<Entry<K, V>> parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<Entry<K, V>> asCollection;

	/**
	 * Creates new MapEntrySetRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	MapEntrySetRequirementsImpl(SingletonScope scope, Map<K, V> parameter, String name,
		Configuration config)
	{
		assert (parameter != null): "parameter may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.map = parameter;
		this.parameter = parameter.entrySet();
		this.name = name + ".entrySet()";
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(scope, this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param scope     the system configuration
	 * @param map       a {@code Map}
	 * @param parameter the entry set of the {@code Map}
	 * @param name      the name of parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code map}, {@code name} or {@code config} are null;
	 *                        if {@code name} is empty
	 */
	private MapEntrySetRequirementsImpl(SingletonScope scope, Map<K, V> map,
		Set<Entry<K, V>> parameter, String name, Configuration config)
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
	public CollectionRequirements<Entry<K, V>> withException(
		Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapEntrySetRequirementsImpl<>(scope, map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapEntrySetRequirementsImpl<>(scope, map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEqualTo(Collection<Entry<K, V>> value)
	{
		asCollection.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEqualTo(Collection<Entry<K, V>> value,
		String name)
	{
		asCollection.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotEqualTo(Collection<Entry<K, V>> value)
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotEqualTo(Collection<Entry<K, V>> value,
		String name)
	{
		asCollection.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isIn(Collection<Collection<Entry<K, V>>> collection)
	{
		asCollection.isIn(collection);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isInstanceOf(Class<?> type)
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNull()
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotNull()
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEmpty()
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
	public CollectionRequirements<Entry<K, V>> isNotEmpty()
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> contains(Entry<K, V> element)
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
	public CollectionRequirements<Entry<K, V>> contains(Entry<K, V> element, String name)
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
	public CollectionRequirements<Entry<K, V>> containsExactly(Collection<Entry<K, V>> elements)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s entries must contain exactly: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsExactly(Collection<Entry<K, V>> elements,
		String name)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s entries must contain exactly %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAny(Collection<Entry<K, V>> elements)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any entry in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
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
	public CollectionRequirements<Entry<K, V>> containsAny(Collection<Entry<K, V>> elements,
		String name)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any entry in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAll(Collection<Entry<K, V>> elements)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all entries in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAll(Collection<Entry<K, V>> elements,
		String name)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all entries in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContain(Entry<K, V> element)
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
	public CollectionRequirements<Entry<K, V>> doesNotContain(Entry<K, V> element, String name)
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
	public CollectionRequirements<Entry<K, V>> doesNotContainAny(Collection<Entry<K, V>> elements)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any entry in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAny(Collection<Entry<K, V>> elements,
		String name)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any entry in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Unwanted", unwanted).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAll(Collection<Entry<K, V>> elements)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all entries in: %s", name, elements)).
			addContext("Actual", parameter).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAll(Collection<Entry<K, V>> elements,
		String name)
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all entries in %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Expected", elements).
			addContext("Map", map).
			build();
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainDuplicates()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(scope, parameter, name, config);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isolate(
		Consumer<CollectionRequirements<Entry<K, V>>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
