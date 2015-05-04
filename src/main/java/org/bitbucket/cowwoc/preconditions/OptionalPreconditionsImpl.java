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
final class OptionalPreconditionsImpl extends ObjectPreconditionsImpl<OptionalPreconditions, Optional<?>>
	implements OptionalPreconditions
{
	/**
	 * Creates new OptionalPreconditionsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	OptionalPreconditionsImpl(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
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
}
