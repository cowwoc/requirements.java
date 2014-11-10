/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;

/**
 * Verifies preconditions of a {@link URI} parameter.
 * <p>
 * @author Gili Tzabari
 */
public final class UriPreconditions extends Preconditions<UriPreconditions, URI>
{
	/**
	 * Creates new UriPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	UriPreconditions(URI parameter, String name)
	{
		super(parameter, name);
	}

	/**
	 * Ensures that the parameter is absolute.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not absolute
	 */
	public UriPreconditions isAbsolute() throws IllegalArgumentException
	{
		if (!parameter.isAbsolute())
			throw new IllegalArgumentException(name + " must be absolute: " + parameter);
		return this;
	}
}
