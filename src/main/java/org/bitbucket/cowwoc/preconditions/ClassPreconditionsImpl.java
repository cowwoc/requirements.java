/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * Default implementation of ClassPreconditions.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
final class ClassPreconditionsImpl<T> extends ObjectPreconditionsImpl<ClassPreconditions<T>, Class<T>>
	implements ClassPreconditions<T>
{
	/**
	 * Creates new ClassPreconditionsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	ClassPreconditionsImpl(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	@Override
	public ClassPreconditions<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(type, "type").isNotNull();
		if (parameter.isAssignableFrom(type))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be a supertype of %s. Was: %s", name, type, parameter.getClass()));
	}
}
