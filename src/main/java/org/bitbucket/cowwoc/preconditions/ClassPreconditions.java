/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * Verifies preconditions of a Class parameter.
 * <p/>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public class ClassPreconditions<T> extends Preconditions<Class<T>>
{
	/**
	 * Creates new ClassPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	ClassPreconditions(String name, Class<T> parameter)
	{
		super(name, parameter);
	}

	/**
	 * Ensures that the parameter is a superclass or superinterface of a class.
	 * <p/>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if {@code parameter} is not a supertype of {@code type}
	 */
	public ClassPreconditions<T> isSupertypeOf(Class<?> type) throws NullPointerException
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!type.isAssignableFrom(parameter))
		{
			Class<?> actualType;
			if (parameter == null)
				actualType = null;
			else
				actualType = parameter.getClass();
			throw new IllegalArgumentException(name + " must be a supertype of " + type + ". Was: " +
				actualType);
		}
		return this;
	}
}
