/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;

/**
 * An implementation of BigDecimalRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalRequirements
	extends AbstractNoOpNumberRequirements<BigDecimalRequirements, BigDecimal>
	implements BigDecimalRequirements
{
	public static final NoOpBigDecimalRequirements INSTANCE = new NoOpBigDecimalRequirements();

	@Override
	public NoOpBigDecimalRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements precision()
	{
		return NoOpBigDecimalPrecisionRequirements.INSTANCE;
	}

	@Override
	public BigDecimalScaleRequirements scale()
	{
		return NoOpBigDecimalScaleRequirements.INSTANCE;
	}
}
