/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Verifies preconditions of an Optional parameter.
 * <p>
 * @author Gili Tzabari
 */
public final class OptionalPreconditions
	extends Preconditions<OptionalPreconditions, Optional<?>>
{
	/**
	 * Creates new OptionalPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	OptionalPreconditions(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is present.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is not present
	 */
	public OptionalPreconditions isPresent()
		throws IllegalArgumentException
	{
		if (!this.parameter.isPresent())
			throw new IllegalArgumentException(name + " must be present");
		return this;
	}

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is present
	 */
	public OptionalPreconditions isEmpty()
		throws IllegalArgumentException
	{
		if (this.parameter.isPresent())
			throw new IllegalArgumentException(name + " must be empty");
		return this;
	}
}
