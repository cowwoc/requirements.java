/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * An implementation of BigDecimalPrecisionRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalPrecisionRequirements extends AbstractNoOpNumberRequirements<BigDecimalPrecisionRequirements, Integer>
	implements BigDecimalPrecisionRequirements
{
	public static final NoOpBigDecimalPrecisionRequirements INSTANCE
		= new NoOpBigDecimalPrecisionRequirements();

	@Override
	public BigDecimalPrecisionRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
