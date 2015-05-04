/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * An implementation of CollectionPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpCollectionPreconditions implements CollectionPreconditions<Object>
{
	INSTANCE;

	@Override
	public CollectionPreconditions<Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public <E extends RuntimeException> CollectionPreconditions<Object> using(Class<E> exception)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object> isEqualTo(Collection<Object> value)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object> isNull()
	{
		return this;
	}

	@Override
	public CollectionPreconditions<Object> isNotNull()
	{
		return this;
	}
}
