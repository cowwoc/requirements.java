/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of ObjectPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpObjectPreconditions implements ObjectPreconditions<NoOpObjectPreconditions, Object>
{
	INSTANCE;

	@Override
	public <E extends RuntimeException> NoOpObjectPreconditions using(Class<E> exception)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isNull()
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isNotNull()
	{
		return this;
	}
}
