/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;

/**
 * Verifies preconditions of a Number parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class UriPreconditions extends Preconditions<URI>
{
	/**
	 * Creates new UriPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	UriPreconditions(String name, URI parameter)
	{
		super(name, parameter);
	}

	@Override
	public UriPreconditions isEqualTo(Object value) throws IllegalArgumentException
	{
		return (UriPreconditions) super.isEqualTo(value);
	}

	@Override
	public UriPreconditions stateIsNotNull() throws NullPointerException
	{
		return (UriPreconditions) super.stateIsNotNull();
	}

	@Override
	public UriPreconditions isNotNull() throws NullPointerException
	{
		return (UriPreconditions) super.isNotNull();
	}

	/**
	 * Ensures that the parameter is absolute.
	 * <p/>
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
