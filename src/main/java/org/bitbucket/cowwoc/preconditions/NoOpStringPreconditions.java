/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;

/**
 * An implementation of StringPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpStringPreconditions implements StringPreconditions
{
	INSTANCE;

	@Override
	public StringPreconditions doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringPreconditions doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringPreconditions endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringPreconditions hasLength(int length)
	{
		return this;
	}

	@Override
	public StringPreconditions hasMaximumLength(int maxLength)
	{
		return this;
	}

	@Override
	public StringPreconditions hasMinimumLength(int minLength)
	{
		return this;
	}

	@Override
	public StringPreconditions isEmailFormat()
	{
		return this;
	}

	@Override
	public StringPreconditions isEmpty()
	{
		return this;
	}

	@Override
	public StringPreconditions isIpAddressFormat()
	{
		return this;
	}

	@Override
	public StringPreconditions isNotEmpty()
	{
		return this;
	}

	@Override
	public StringPreconditions lengthIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public StringPreconditions startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringPreconditions trim()
	{
		return this;
	}

	@Override
	public <E extends RuntimeException> StringPreconditions using(Class<E> exception)
	{
		return this;
	}

	@Override
	public StringPreconditions isEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public StringPreconditions isNull()
	{
		return this;
	}

	@Override
	public StringPreconditions isNotNull()
	{
		return this;
	}
}
