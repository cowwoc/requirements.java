/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * An implementation of BigDecimalPrecisionPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalPrecisionPreconditions extends AbstractNoOpNumberPreconditions<BigDecimalPrecisionPreconditions, Integer>
	implements BigDecimalPrecisionPreconditions
{
	public static final NoOpBigDecimalPrecisionPreconditions INSTANCE
		= new NoOpBigDecimalPrecisionPreconditions();

	@Override
	public BigDecimalPrecisionPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
