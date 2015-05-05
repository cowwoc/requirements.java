/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of ClassPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpClassPreconditions implements ClassPreconditions<Object>
{
	INSTANCE;

	@Override
	public ClassPreconditions<Object> isSupertypeOf(Class<?> type)
	{
		return this;
	}

	@Override
	public NoOpClassPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ClassPreconditions<Object> isEqualTo(Class<Object> value)
	{
		return this;
	}

	@Override
	public ClassPreconditions<Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassPreconditions<Object> isNull()
	{
		return this;
	}

	@Override
	public ClassPreconditions<Object> isNotNull()
	{
		return this;
	}
}
