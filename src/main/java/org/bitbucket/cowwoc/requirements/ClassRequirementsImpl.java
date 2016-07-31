/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;

/**
 * Default implementation of ClassRequirements.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
final class ClassRequirementsImpl<T> extends AbstractObjectRequirements<ClassRequirements<T>, Class<T>>
	implements ClassRequirements<T>
{
	/**
	 * Creates new ClassRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ClassRequirementsImpl(Class<T> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public ClassRequirements<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(type, "type").isNotNull();
		if (parameter.isAssignableFrom(type))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be a supertype of %s\n" +
				"Actual: %s", name, type, parameter.getClass()));
	}

	@Override
	public ClassRequirements<T> usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new ClassRequirementsImpl<>(parameter, name, exceptionOverride);
	}
}
