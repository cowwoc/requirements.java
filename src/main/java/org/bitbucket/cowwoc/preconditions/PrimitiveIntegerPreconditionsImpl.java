/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Verifies preconditions of an {@link int} parameter.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @author Gili Tzabari
 */
abstract class PrimitiveIntegerPreconditionsImpl<S extends PrimitiveIntegerPreconditions<S>>
	extends NumberPreconditionsImpl<S, Integer>
	implements PrimitiveIntegerPreconditions<S>
{
	/**
	 * Creates new PrimitiveIntegerPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	protected PrimitiveIntegerPreconditionsImpl(Integer parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public S isNotNull() throws NullPointerException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public S isNull() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be null", name));
	}
}
