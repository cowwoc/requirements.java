/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * An implementation of BigDecimalScaleRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalScaleRequirements extends AbstractNoOpNumberRequirements<BigDecimalScaleRequirements, Integer>
	implements BigDecimalScaleRequirements
{
	public static final NoOpBigDecimalScaleRequirements INSTANCE
		= new NoOpBigDecimalScaleRequirements();

	@Override
	public BigDecimalScaleRequirements usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}
}
