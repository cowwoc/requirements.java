/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of StringRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpStringRequirements implements StringRequirements
{
	INSTANCE;

	@Override
	public StringRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public StringRequirements withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public StringRequirements doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringRequirements doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringRequirements endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringRequirements isEmailFormat()
	{
		return this;
	}

	@Override
	public StringRequirements isIpAddressFormat()
	{
		return this;
	}

	@Override
	public StringRequirements isEmpty()
	{
		return this;
	}

	@Override
	public StringRequirements isNotEmpty()
	{
		return this;
	}

	@Override
	public StringRequirements startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringRequirements trim()
	{
		return this;
	}

	@Override
	public StringRequirements isEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringRequirements isEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringRequirements isNotEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringRequirements isNotEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public StringRequirements isNull()
	{
		return this;
	}

	@Override
	public StringRequirements isNotNull()
	{
		return this;
	}

	@Override
	public StringLengthRequirements length()
	{
		return NoOpStringLengthRequirements.INSTANCE;
	}

	@Override
	public StringRequirements isolate(Consumer<StringRequirements> consumer)
	{
		return this;
	}
}
