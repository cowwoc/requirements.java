package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * Verifies preconditions of a Collection parameter.
 * <p/>
 * @param <E> the type of element in the collection
 * @author Gili Tzabari
 */
public final class CollectionPreconditions<E> extends Preconditions<Collection<E>>
{
	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	CollectionPreconditions(String name, Collection<E> parameter)
	{
		super(name, parameter);
	}

	@Override
	public CollectionPreconditions<E> isEqualTo(Object value) throws IllegalArgumentException
	{
		return (CollectionPreconditions<E>) super.isEqualTo(value);
	}

	@Override
	public CollectionPreconditions<E> stateIsNotNull() throws NullPointerException
	{
		return (CollectionPreconditions<E>) super.stateIsNotNull();
	}

	@Override
	public CollectionPreconditions<E> isNotNull() throws NullPointerException
	{
		return (CollectionPreconditions<E>) super.isNotNull();
	}

	/**
	 * Ensures that the parameter is not empty.
	 * <p/>
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
