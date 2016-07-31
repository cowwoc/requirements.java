/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;

/**
 * Default implementation of ObjectRequirements.
 * <p>
 * @param <S> the type of requirements that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class ObjectRequirementsImpl<S extends ObjectRequirements<S, T>, T>
	extends AbstractObjectRequirements<S, T>
	implements ObjectRequirements<S, T>
{
	/**
	 * Creates new ObjectRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ObjectRequirementsImpl(T parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public S usingException(Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return self;
		@SuppressWarnings("unchecked")
		S result = (S) new ObjectRequirementsImpl<>(parameter, name, exceptionOverride);
		return result;
	}
}
