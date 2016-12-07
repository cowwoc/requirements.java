/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;

/**
 * An implementation of UriVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpUriVerifier implements UriVerifier
{
	INSTANCE;

	@Override
	public UriVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public UriVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public UriVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public UriVerifier isAbsolute()
	{
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriVerifier isIn(Collection<URI> collection)
	{
		return this;
	}

	@Override
	public UriVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public UriVerifier isNull()
	{
		return this;
	}

	@Override
	public UriVerifier isNotNull()
	{
		return this;
	}

	@Override
	public UriVerifier isolate(Consumer<UriVerifier> consumer)
	{
		return this;
	}
}
