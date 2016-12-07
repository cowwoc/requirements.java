/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Collections;
import org.bitbucket.cowwoc.requirements.util.Sets;
import org.bitbucket.cowwoc.requirements.util.Strings;

/**
 * Default implementation of {@code CollectionVerifier}.
 * <p>
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
class CollectionVerifierImpl<E> implements CollectionVerifier<E>
{
	private final SingletonScope scope;
	private final Collection<E> actual;
	private final String name;
	private final Configuration config;
	private final Pluralizer pluralizer;
	private final ObjectVerifier<Collection<E>> asObject;

	/**
	 * Creates new CollectionVerifierImpl.
	 * <p>
	 * @param scope      the system configuration
	 * @param actual     the value of the parameter
	 * @param name       the name of the parameter
	 * @param config     the instance configuration
	 * @param pluralizer returns the singular or plural form of an element type
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	CollectionVerifierImpl(SingletonScope scope, Collection<E> actual, String name,
		Configuration config, Pluralizer pluralizer)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		assert (pluralizer != null): "pluralizer may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.pluralizer = pluralizer;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public CollectionVerifier<E> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new CollectionVerifierImpl<>(scope, actual, name, newConfig, pluralizer);
	}

	@Override
	public CollectionVerifier<E> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new CollectionVerifierImpl<>(scope, actual, name, newConfig, pluralizer);
	}

	@Override
	public CollectionVerifier<E> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionVerifierImpl<>(scope, actual, name, newConfig, pluralizer);
	}

	@Override
	public CollectionVerifier<E> isEqualTo(Collection<E> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public CollectionVerifier<E> isEqualTo(Collection<E> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotEqualTo(Collection<E> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public CollectionVerifier<E> isIn(Collection<Collection<E>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public CollectionVerifier<E> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionVerifier<E> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public CollectionVerifier<E> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public CollectionVerifier<E> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<E> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionVerifier<E> contains(E element)
	{
		if (actual.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<E> contains(E element, String name)
	{
		scope.getInternalVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Missing", element).
			build();
	}

	@Override
	public CollectionVerifier<E> containsExactly(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat(elements, "elements").isNotNull();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> missing = Sets.difference(elementsAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain exactly: %s", name, elements)).
			addContext("Actual", actual).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<E> containsExactly(Collection<E> elements, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> missing = Sets.difference(elementsAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain exactly the same %s as %s.", this.name, pluralizer.nameOf(2),
				name)).
			addContext("Actual", actual).
			addContext("Expected", elements).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<E> containsAny(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat(elements, "elements").isNotNull();
		if (actualContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any %s in: %s", name, pluralizer.nameOf(1), elements)).
			addContext("Actual", actual).
			build();
	}

	/**
	 * @param elements a collection of elements
	 * @return true if actual value contains any of {@code elements}
	 */
	private boolean actualContainsAny(Collection<E> elements)
	{
		for (E element: elements)
			if (actual.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionVerifier<E> containsAny(Collection<E> elements, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actualContainsAny(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain any %s in %s.", this.name, pluralizer.nameOf(1), name)).
			addContext("Actual", actual).
			addContext("Missing", elements).
			build();
	}

	@Override
	public CollectionVerifier<E> containsAll(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat(elements, "elements").isNotNull();
		if (actual.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> missing = Sets.difference(elementsAsSet, actualAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all %s in: %s", name, pluralizer.nameOf(2), elements)).
			addContext("Actual", actual).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionVerifier<E> containsAll(Collection<E> elements, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> missing = Sets.difference(elementsAsSet, actualAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain all %s in %s.", this.name, pluralizer.nameOf(2), name)).
			addContext("Actual", actual).
			addContext("Required", elements).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContain(E element)
	{
		if (!actual.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain: %s", name, element)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContain(E element, String name)
	{
		scope.getInternalVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!actual.contains(element))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Unwanted", element).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContainAny(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat(elements, "elements").isNotNull();
		if (!actualContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any %s in: %s", name, pluralizer.nameOf(1), elements)).
			addContext("Actual", actual).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContainAny(Collection<E> elements, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!actualContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> actualAsSet = Collections.asSet(actual);
		Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain any %s in %s.", this.name, pluralizer.nameOf(1), name)).
			addContext("Actual", actual).
			addContext(Strings.capitalize(pluralizer.nameOf(2)), elements).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContainAll(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat(elements, "elements").isNotNull();
		if (!actual.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all of: %s", name, elements)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContainAll(Collection<E> elements, String name)
	{
		UnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!actual.containsAll(elements))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain all %s in %s.", this.name, pluralizer.nameOf(2), name)).
			addContext("Actual", actual).
			addContext("Unwanted", elements).
			build();
	}

	@Override
	public CollectionVerifier<E> doesNotContainDuplicates()
	{
		if (actual instanceof Set)
			return this;
		int size = actual.size();
		Set<E> unique = new HashSet<>(size);
		Set<E> duplicates = new HashSet<>(size);
		for (E element: actual)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		if (duplicates.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain duplicate %s.", name, pluralizer.nameOf(2))).
			addContext("Actual", actual).
			addContext("Duplicates", duplicates).
			build();
	}

	@Override
	public ContainerSizeVerifier size()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.size(), name,
			name + ".size()", pluralizer, config);
	}

	@Override
	public CollectionVerifier<E> isolate(Consumer<CollectionVerifier<E>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
