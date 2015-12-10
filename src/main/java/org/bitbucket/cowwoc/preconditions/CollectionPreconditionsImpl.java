/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of CollectionPreconditions.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
class CollectionPreconditionsImpl<E, T extends Collection<E>>
	extends AbstractObjectPreconditions<CollectionPreconditions<E, T>, T>
	implements CollectionPreconditions<E, T>
{
	/**
	 * Creates new CollectionPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	CollectionPreconditionsImpl(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionPreconditions<E, T> isEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty.\n" +
			"Actual: %s", name, parameter));
	}

	@Override
	public CollectionPreconditions<E, T> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	@Override
	public CollectionPreconditions<E, T> contains(E element) throws IllegalArgumentException
	{
		if (parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must contain %s.\n" +
			"Actual: %s", name, element, parameter));
	}

	@Override
	public CollectionPreconditions<E, T> containsAny(Collection<E> elements)
		throws IllegalArgumentException
	{
		for (E element: elements)
			if (parameter.contains(element))
				return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain any of %s.\n" +
				"Actual: %s", name, elements, parameter));
	}

	@Override
	public CollectionPreconditions<E, T> containsAll(Collection<E> elements)
		throws IllegalArgumentException
	{
		if (parameter.containsAll(elements))
			return this;
		Set<E> missing = new HashSet<>(elements);
		for (Iterator<E> i = missing.iterator(); i.hasNext();)
		{
			if (parameter.contains(i.next()))
				i.remove();
		}
		return throwException(IllegalArgumentException.class,
			String.format("%s must contain %s.\n" +
				"Actual : %s\n" +
				"Missing: %s", name, elements, parameter, missing));
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContain(E element) throws IllegalArgumentException
	{
		if (!parameter.contains(element))
			return this;
		return throwException(IllegalArgumentException.class, String.format(
			"%s must not contain %s.\n" +
			"Actual: %s", name, element, parameter));
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAny(Collection<E> elements)
		throws IllegalArgumentException
	{
		boolean matchFound = false;
		for (E element: elements)
		{
			if (parameter.contains(element))
			{
				matchFound = true;
				break;
			}
		}
		if (!matchFound)
			return this;
		Set<E> unwanted = new HashSet<>(elements);
		for (Iterator<E> i = unwanted.iterator(); i.hasNext();)
		{
			if (!parameter.contains(i.next()))
				i.remove();
		}
		return throwException(IllegalArgumentException.class, String.format(
			"%s must not contain any of %s.\n" +
			"Actual  : %s\n" +
			"Unwanted: %s", name, elements, parameter, unwanted));
	}

	@Override
	public CollectionPreconditions<E, T> doesNotContainAll(Collection<E> elements) throws
		IllegalArgumentException
	{
		if (!parameter.containsAll(elements))
			return this;
		return throwException(IllegalArgumentException.class, String.format(
			"%s must not contain all of %s.\n" +
			"Actual: %s", name, elements, parameter));
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return new CollectionSizePreconditionsImpl(parameter, name, exceptionOverride);
	}

	@Override
	protected CollectionPreconditions<E, T> valueOf(T parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		return new CollectionPreconditionsImpl<>(parameter, name, exceptionOverride);
	}
}
