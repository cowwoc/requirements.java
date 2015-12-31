/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Default implementation of OptionalPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class OptionalPreconditionsImpl extends AbstractObjectPreconditions<OptionalPreconditions, Optional<?>>
	implements OptionalPreconditions
{
	/**
	 * Creates new OptionalPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} or {@code exceptionOverride} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	OptionalPreconditionsImpl(Optional<?> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public OptionalPreconditions isPresent()
		throws IllegalArgumentException
	{
		if (parameter.isPresent())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be present", name));
	}

	@Override
	public OptionalPreconditions isEmpty()
		throws IllegalArgumentException
	{
		if (!parameter.isPresent())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty", name));
	}

	@Override
	protected OptionalPreconditions valueOf(Optional<?> parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		if (exceptionOverride.equals(this.exceptionOverride))
			return this;
		return new OptionalPreconditionsImpl(parameter, name, exceptionOverride);
	}
}
