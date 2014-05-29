/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * Verifies preconditions of a Collection parameter.
 * <p>
 * @param <E> the type of element in the collection
 * @author Gili Tzabari
 */
public final class CollectionPreconditions<E> extends
	Preconditions<CollectionPreconditions<E>, Collection<E>>
{
	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	CollectionPreconditions(Collection<E> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	public CollectionPreconditions<E> isNotEmpty() throws IllegalArgumentException
	{
		if (parameter.isEmpty())
			throw new IllegalArgumentException(name + " may not be empty");
		return this;
	}
}
