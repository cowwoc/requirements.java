/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of DoublePreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpDoublePreconditions extends AbstractNoOpNumberPreconditions<DoublePreconditions, Double>
	implements DoublePreconditions
{
	public static final NoOpDoublePreconditions INSTANCE = new NoOpDoublePreconditions();

	@Override
	public NoOpDoublePreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
