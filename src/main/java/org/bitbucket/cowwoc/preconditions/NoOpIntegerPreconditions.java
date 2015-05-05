/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of IntegerPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpIntegerPreconditions extends AbstractNoOpNumberPreconditions<IntegerPreconditions, Integer>
	implements IntegerPreconditions
{
	public static final NoOpIntegerPreconditions INSTANCE = new NoOpIntegerPreconditions();

	@Override
	public NoOpIntegerPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
