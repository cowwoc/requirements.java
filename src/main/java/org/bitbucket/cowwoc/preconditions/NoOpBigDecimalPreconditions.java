/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.math.BigDecimal;

/**
 * An implementation of BigDecimalPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalPreconditions extends AbstractNoOpNumberPreconditions<BigDecimalPreconditions, BigDecimal>
	implements BigDecimalPreconditions
{
	public static final NoOpBigDecimalPreconditions INSTANCE = new NoOpBigDecimalPreconditions();

	@Override
	public NoOpBigDecimalPreconditions usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
