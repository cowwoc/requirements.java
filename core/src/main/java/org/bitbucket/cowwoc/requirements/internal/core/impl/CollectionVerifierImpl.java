/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.internal.core.util.Sets;
import org.bitbucket.cowwoc.requirements.internal.core.util.Strings;

/**
 * Default implementation of {@link CollectionVerifier}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public class CollectionVerifierImpl<C extends Collection<E>, E>
	extends ObjectCapabilitiesImpl<CollectionVerifier<C, E>, C>
	implements CollectionVerifier<C, E>
{
	private final Pluralizer pluralizer;

	/**
	 * Creates new CollectionVerifierImpl.
	 *
	 * @param scope      the application configuration
	 * @param name       the name of the value
	 * @param actual     the actual value
	 * @param pluralizer returns the singular or plural form of an element type
	 * @param config     the instance configuration
	 * @throws AssertionError if {@code name}, {@code pluralizer} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public CollectionVerifierImpl(ApplicationScope scope, String name, C actual,
		Pluralizer pluralizer, Configuration config)
	{
		super(scope, name, actual, config);
		assert (pluralizer != null): "pluralizer may not be null";
		this.pluralizer = pluralizer;
	}

	@Override
	public CollectionVerifier<C, E> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<C, E> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be empty.", name)).
			build();
	}

	@Override
	public CollectionVerifier<C, E> contains(E element)
	{
		if (actual.contains(element))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain: %s", name, element)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<C, E> contains(String name, E element)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (actual.contains(element))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Missing", element).
			build();
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(Collection<E> expected)
	{
		scope.getInternalVerifier().requireThat("expected", expected).isNotNull();
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain exactly: %s", name, expected)).
			addContext("Actual", actual).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<C, E> containsExactly(String name, Collection<E> expected)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("expected", expected).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain exactly the same %s as %s.", this.name, pluralizer.nameOf(2),
				name)).
			addContext("Actual", actual).
			addContext("Expected", expected).
			addContext("Missing", missing).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<C, E> containsAny(Collection<E> expected)
	{
		scope.getInternalVerifier().requireThat("expected", expected).isNotNull();
		if (actualContainsAny(expected))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain any %s in: %s", name, pluralizer.nameOf(1), expected)).
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
	public CollectionVerifier<C, E> containsAny(String name, Collection<E> expected)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("expected", expected).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (actualContainsAny(expected))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain any %s in %s.", this.name, pluralizer.nameOf(1), name)).
			addContext("Actual", actual).
			addContext("Missing", expected).
			build();
	}

	@Override
	public CollectionVerifier<C, E> containsAll(Collection<E> expected)
	{
		scope.getInternalVerifier().requireThat("expected", expected).isNotNull();
		if (actual.containsAll(expected))
			return this;
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain all %s in: %s", name, pluralizer.nameOf(2), expected)).
			addContext("Actual", actual).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionVerifier<C, E> containsAll(String name, Collection<E> expected)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("expected", expected).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (actual.containsAll(expected))
			return this;
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain all %s in %s.", this.name, pluralizer.nameOf(2), name)).
			addContext("Actual", actual).
			addContext("Expected", expected).
			addContext("Missing", missing).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(E element)
	{
		if (!actual.contains(element))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain: %s", name, element)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContain(String name, E element)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (!actual.contains(element))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Unwanted", element).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat("elements", elements).isNotNull();
		if (!actualContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Sets.fromCollection(elements);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain any %s in: %s", name, pluralizer.nameOf(1), elements)).
			addContext("Actual", actual).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAny(String name, Collection<E> elements)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("elements", elements).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (!actualContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Sets.fromCollection(elements);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain any %s in %s.", this.name, pluralizer.nameOf(1), name)).
			addContext("Actual", actual).
			addContext(Strings.capitalize(pluralizer.nameOf(2)), elements).
			addContext("Unwanted", unwanted).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements)
	{
		scope.getInternalVerifier().requireThat("elements", elements).isNotNull();
		if (!actual.containsAll(elements))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain all of: %s", name, elements)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainAll(String name, Collection<E> elements)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("elements", elements).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (!actual.containsAll(elements))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain all %s in %s.", this.name, pluralizer.nameOf(2), name)).
			addContext("Actual", actual).
			addContext("Unwanted", elements).
			build();
	}

	@Override
	public CollectionVerifier<C, E> doesNotContainDuplicates()
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
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain duplicate %s.", name, pluralizer.nameOf(2))).
			addContext("Actual", actual).
			addContext("Duplicates", duplicates).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> size()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".size()", actual.size(),
			pluralizer, config);
	}

	@Override
	public CollectionVerifier<C, E> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(size());
		return this;
	}

	@Override
	public ArrayVerifier<E> asArray(Class<E> type)
	{
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(type, actual.size());
		return new ArrayVerifierImpl<>(scope, name, actual.toArray(array), config);
	}

	@Override
	public CollectionVerifier<C, E> asArray(Class<E> type, Consumer<ArrayVerifier<E>> consumer)
	{
		consumer.accept(asArray(type));
		return this;
	}
}
