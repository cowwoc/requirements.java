/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies a {@link BigDecimal#scale()}.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalScaleRequirements
	extends PrimitiveIntegerRequirementsSpi<BigDecimalScaleRequirements>,
	Isolatable<BigDecimalScaleRequirements>
{
}
