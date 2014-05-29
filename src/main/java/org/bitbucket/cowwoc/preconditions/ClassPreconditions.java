/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * Verifies preconditions of a Class parameter.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public class ClassPreconditions<T> extends Preconditions<ClassPreconditions<T>, Class<T>>
{
	/**
	 * Creates new ClassPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	ClassPreconditions(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is a superclass or superinterface of a class.
	 * <p>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code parameter} or {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not a supertype of {@code type}
	 */
	public ClassPreconditions<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		if (parameter == null)
			throw new NullPointerException("parameter may not be null");
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!parameter.isAssignableFrom(type))
		{
			throw new IllegalArgumentException(name + " must be a supertype of " + type + ". Was: " +
				parameter.getClass());
		}
		return this;
	}
}
