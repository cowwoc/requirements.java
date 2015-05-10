/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of MapSizePreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpMapSizePreconditions
	extends AbstractNoOpNumberPreconditions<MapSizePreconditions, Integer>
	implements MapSizePreconditions
{
	public static final NoOpMapSizePreconditions INSTANCE = new NoOpMapSizePreconditions();

	@Override
	public MapSizePreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
