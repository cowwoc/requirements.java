/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of CollectionSizePreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpCollectionSizePreconditions
	extends AbstractNoOpNumberPreconditions<CollectionSizePreconditions, Integer>
	implements CollectionSizePreconditions
{
	public static final NoOpCollectionSizePreconditions INSTANCE
		= new NoOpCollectionSizePreconditions();

	@Override
	public CollectionSizePreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
