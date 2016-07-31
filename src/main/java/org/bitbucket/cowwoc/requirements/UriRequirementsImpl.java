/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import java.util.Objects;

/**
 * Default implementation of UriRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class UriRequirementsImpl extends AbstractObjectRequirements<UriRequirements, URI>
	implements UriRequirements
{
	/**
	 * Creates new UriRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	UriRequirementsImpl(URI parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public UriRequirements isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be absolute.\n" +
				"Actual: %s", name, parameter));
	}

	@Override
	public UriRequirements usingException(Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new UriRequirementsImpl(parameter, name, exceptionOverride);
	}
}
