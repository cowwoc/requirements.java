/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of StringLengthPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpStringLengthPreconditions extends AbstractNoOpNumberPreconditions<StringLengthPreconditions, Integer>
	implements StringLengthPreconditions
{
	public static final NoOpStringLengthPreconditions INSTANCE
		= new NoOpStringLengthPreconditions();

	@Override
	public StringLengthPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
