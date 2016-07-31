/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of OptionalRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class OptionalRequirementsImpl extends AbstractObjectRequirements<OptionalRequirements, Optional<?>>
	implements OptionalRequirements
{
	/**
	 * Creates new OptionalRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	OptionalRequirementsImpl(Optional<?> parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public OptionalRequirements isPresent() throws IllegalArgumentException
	{
		if (parameter.isPresent())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be present", name));
	}

	@Override
	public OptionalRequirements isEmpty() throws IllegalArgumentException
	{
		if (!parameter.isPresent())
			return this;
		return throwException(IllegalArgumentException.class, String.format("%s must be empty", name));
	}

	@Override
	public OptionalRequirements usingException(Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new OptionalRequirementsImpl(parameter, name, exceptionOverride);
	}
}
