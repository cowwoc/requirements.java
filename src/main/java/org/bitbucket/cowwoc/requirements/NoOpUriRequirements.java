/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of UriRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpUriRequirements implements UriRequirements
{
	INSTANCE;

	@Override
	public UriRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public UriRequirements withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public UriRequirements isAbsolute()
	{
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriRequirements isEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriRequirements isNotEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriRequirements isIn(Collection<URI> collection)
	{
		return this;
	}

	@Override
	public UriRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public UriRequirements isNull()
	{
		return this;
	}

	@Override
	public UriRequirements isNotNull()
	{
		return this;
	}

	@Override
	public UriRequirements isolate(Consumer<UriRequirements> consumer)
	{
		return this;
	}
}
