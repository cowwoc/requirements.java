/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of MapRequirements.entrySet().
 * <p>
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 * @author Gili Tzabari
 */
final class MapEntrySetRequirementsImpl<K, V> implements CollectionRequirements<Entry<K, V>>
{
	private final Map<K, V> map;
	private final Set<Entry<K, V>> parameter;
	private final String name;
	private final Configuration config;
	private final CollectionRequirements<Entry<K, V>> asCollection;

	/**
	 * Creates new MapEntrySetRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapEntrySetRequirementsImpl(Map<K, V> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.map = parameter;
		this.parameter = parameter.entrySet();
		this.name = name + ".entrySet()";
		this.config = config;
		this.asCollection = new CollectionRequirementsImpl<>(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param map       a {@code Map}
	 * @param parameter the entry set of the {@code Map}
	 * @param name      the name of parameter
	 * @param config    determines the behavior of this verifier
	 */
	private MapEntrySetRequirementsImpl(Map<K, V> map, Set<Entry<K, V>> parameter, String name,
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
	public CollectionRequirements<Entry<K, V>> withException(
		Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapEntrySetRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapEntrySetRequirementsImpl<>(map, parameter, name, newConfig);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEqualTo(Collection<Entry<K, V>> value)
		throws IllegalArgumentException
	{
		asCollection.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEqualTo(Collection<Entry<K, V>> value,
		String name) throws NullPointerException, IllegalArgumentException
	{
		asCollection.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotEqualTo(Collection<Entry<K, V>> value)
		throws IllegalArgumentException
	{
		asCollection.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotEqualTo(Collection<Entry<K, V>> value,
		String name) throws NullPointerException, IllegalArgumentException
	{
		asCollection.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asCollection.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNull() throws IllegalArgumentException
	{
		asCollection.isNull();
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotNull() throws NullPointerException
	{
		asCollection.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isEmpty()
		throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty.", name),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> isNotEmpty()
		throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> contains(Entry<K, V> element)
		throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element),
			"Actual", map);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> contains(Entry<K, V> element,
		String name) throws NullPointerException, IllegalArgumentException
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
	public CollectionRequirements<Entry<K, V>> containsExactly(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s entries must contain exactly: %s", name, elements),
			"Actual", parameter,
			"Missing", missing,
			"Unwanted", unwanted);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsExactly(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<Entry<K, V>> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s entries must contain exactly %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAny(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
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
	public CollectionRequirements<Entry<K, V>> containsAny(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain any entry in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAll(
		Collection<Entry<K, V>> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all entries in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, map, missing));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> containsAll(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> missing = Sets.difference(elementsAsSet, parameterAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain all entries in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s", this.name, name, elements, map, missing));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContain(
		Entry<K, V> element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, map));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContain(
		Entry<K, V> element, String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, element, map));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAny(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any entry in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, map, unwanted));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAny(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<Entry<K, V>> elementsAsSet = Collections.asSet(elements);
		Set<Entry<K, V>> parameterAsSet = Collections.asSet(parameter);
		Set<Entry<K, V>> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain any entry in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, map, unwanted));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAll(
		Collection<Entry<K, V>> elements) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all entries in: %s\n" +
				"Actual: %s", name, elements, map));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainAll(
		Collection<Entry<K, V>> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not contain all entries in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, map));
	}

	@Override
	public CollectionRequirements<Entry<K, V>> doesNotContainDuplicates()
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
	public CollectionRequirements<Entry<K, V>> isolate(
		Consumer<CollectionRequirements<Entry<K, V>>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
