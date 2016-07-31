/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * Verifies requirements of an {@link int} parameter.
 * <p>
 * @param <S> the type of requirements that was instantiated
 * @author Gili Tzabari
 */
abstract class PrimitiveIntegerRequirementsImpl<S extends PrimitiveIntegerRequirements<S>>
	extends NumberRequirementsImpl<S, Integer>
	implements PrimitiveIntegerRequirements<S>
{
	/**
	 * Creates new PrimitiveIntegerRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	protected PrimitiveIntegerRequirementsImpl(Integer parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
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
