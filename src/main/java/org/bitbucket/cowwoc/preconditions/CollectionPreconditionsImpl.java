/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;
import java.util.Optional;

/**
 * Default implementation of CollectionPreconditions.
 * <p>
 * @param <E> the type of element in the collection
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
class CollectionPreconditionsImpl<E, T extends Collection<E>>
	extends ObjectPreconditionsImpl<CollectionPreconditions<E, T>, T>
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
	public CollectionPreconditions<E, T> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}

	@Override
	public CollectionSizePreconditions size()
	{
		return new CollectionSizePreconditionsImpl(parameter, name, exceptionOverride);
	}
}
