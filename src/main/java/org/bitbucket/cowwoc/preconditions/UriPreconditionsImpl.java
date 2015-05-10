/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;
import java.util.Optional;

/**
 * Default implementation of UriPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class UriPreconditionsImpl extends ObjectPreconditionsImpl<UriPreconditions, URI>
	implements UriPreconditions
{
	/**
	 * Creates new UriPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	UriPreconditionsImpl(URI parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public UriPreconditions isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must be absolute: %s", name, parameter));
	}
}