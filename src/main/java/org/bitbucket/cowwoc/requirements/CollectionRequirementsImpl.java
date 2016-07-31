/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Default implementation of CollectionRequirementsForExtension.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
class CollectionRequirementsImpl<E, T extends Collection<E>>
	extends AbstractObjectRequirements<CollectionRequirements<E, T>, T>
	implements CollectionRequirements<E, T>
{
	/**
	 * Creates new CollectionRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionRequirementsImpl(T parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<E, T> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be empty.\n" +
				"Actual: %s", name, parameter));
	}

	@Override
	public CollectionRequirements<E, T> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not be empty.", name));
	}

	@Override
	public CollectionRequirements<E, T> contains(E element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain: %s\n" +
				"Actual: %s", name, element, parameter));
	}

	@Override
	public CollectionRequirements<E, T> contains(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s\n" +
				"Actual : %s\n" +
				"Missing: %s", this.name, name, parameter, element));
	}

	@Override
	public CollectionRequirements<E, T> containsExactly(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<E> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s elements must contain exactly: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<E, T> containsExactly(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		Set<E> unwanted = Sets.difference(parameterAsSet, elementsAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s elements must contain exactly %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, missing, unwanted));
	}

	@Override
	public CollectionRequirements<E, T> containsAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any element in: %s\n" +
				"Actual: %s", name, elements, parameter));
	}

	/**
	 * @param elements a collection of elements
	 * @return true if {@code parameter} contains any of {@code elements}
	 */
	private boolean parameterContainsAny(Collection<E> elements)
	{
		for (E element: elements)
			if (parameter.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionRequirements<E, T> containsAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameterContainsAny(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any element in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, parameter));
	}

	@Override
	public CollectionRequirements<E, T> containsAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (parameter.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all elements in: %s\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, parameter, missing));
	}

	@Override
	public CollectionRequirements<E, T> containsAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter.containsAll(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> missing = Sets.difference(elementsAsSet, parameterAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain all elements in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Missing : %s", this.name, name, elements, parameter, missing));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContain(E element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain: %s\n" +
				"Actual: %s", name, element, parameter));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, element, parameter));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameterContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any element in: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", name, elements, parameter, unwanted));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAny(Collection<E> elements,
		String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameterContainsAny(elements))
			return this;
		Set<E> elementsAsSet = Collections.asSet(elements);
		Set<E> parameterAsSet = Collections.asSet(parameter);
		Set<E> unwanted = Sets.intersection(parameterAsSet, elementsAsSet);
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain any element in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s\n" +
				"Unwanted: %s", this.name, name, elements, parameter, unwanted));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all of: %s\n" +
				"Actual: %s", name, elements, parameter));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(elements, "elements").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain all elements in %s\n" +
				"Expected: %s\n" +
				"Actual  : %s", this.name, name, elements, parameter));
	}

	@Override
	public CollectionRequirements<E, T> doesNotContainDuplicates() throws IllegalArgumentException
	{
		if (parameter instanceof Set)
			return this;
		int size = parameter.size();
		Set<E> unique = new HashSet<>(size);
		Set<E> duplicates = new HashSet<>(size);
		for (E element: parameter)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		if (duplicates.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s may not contain duplicate elements\n" +
				"Actual: %s\n" +
				"Duplicates: %s", name, parameter, duplicates));
	}

	@Override
	public CollectionSizeRequirements size()
	{
		return new CollectionSizeRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionRequirements<E, T> usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new CollectionRequirementsImpl<>(parameter, name, exceptionOverride);
	}
}
