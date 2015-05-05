/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of BigDecimalScalePreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalScalePreconditions extends AbstractNoOpNumberPreconditions<BigDecimalScalePreconditions, Integer>
	implements BigDecimalScalePreconditions
{
	public static final NoOpBigDecimalScalePreconditions INSTANCE
		= new NoOpBigDecimalScalePreconditions();

	@Override
	public BigDecimalScalePreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
