/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

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
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	ClassPreconditionsImpl(Class<T> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public ClassPreconditions<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Preconditions.requireThat(type, "type").isNotNull();
		if (parameter.isAssignableFrom(type))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be a supertype of %s\n" +
				"Actual  : %s", name, type, parameter.getClass()));
	}
}
