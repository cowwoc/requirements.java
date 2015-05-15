/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;

/**
 * An implementation of UriPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpUriPreconditions implements UriPreconditions
{
	INSTANCE;

	@Override
	public UriPreconditions isAbsolute()
	{
		return this;
	}

	@Override
	public NoOpUriPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public UriPreconditions isEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriPreconditions isEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public UriPreconditions isNull()
	{
		return this;
	}

	@Override
	public UriPreconditions isNotNull()
	{
		return this;
	}
}
