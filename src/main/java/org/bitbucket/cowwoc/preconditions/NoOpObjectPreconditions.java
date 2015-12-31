/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.function.Consumer;

/**
 * An implementation of ObjectPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpObjectPreconditions implements ObjectPreconditions<NoOpObjectPreconditions, Object>
{
	INSTANCE;

	@Override
	public NoOpObjectPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public NoOpObjectPreconditions isNotEqualTo(Object value, String name)
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

	@Override
	public NoOpObjectPreconditions isolate(Consumer<NoOpObjectPreconditions> consumer)
	{
		return this;
	}
}
