/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of StringVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpStringVerifier implements StringVerifier
{
	INSTANCE;

	@Override
	public StringVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public StringVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public StringVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier isEmailFormat()
	{
		return this;
	}

	@Override
	public StringVerifier isIpAddressFormat()
	{
		return this;
	}

	@Override
	public StringVerifier isEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier trim()
	{
		return this;
	}

	@Override
	public StringVerifier isEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringVerifier isEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringVerifier isIn(Collection<String> collection)
	{
		return this;
	}

	@Override
	public StringVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public StringVerifier isNull()
	{
		return this;
	}

	@Override
	public StringVerifier isNotNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public StringVerifier isolate(Consumer<StringVerifier> consumer)
	{
		return this;
	}
}
