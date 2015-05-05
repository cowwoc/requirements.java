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
 * @author Gili Tzabari
 */
final class CollectionPreconditionsImpl<E>
	extends ObjectPreconditionsImpl<CollectionPreconditions<E>, Collection<E>>
	implements CollectionPreconditions<E>
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
	CollectionPreconditionsImpl(Collection<E> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public CollectionPreconditions<E> isNotEmpty() throws IllegalArgumentException
	{
		if (!parameter.isEmpty())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s may not be empty", name));
	}
}
