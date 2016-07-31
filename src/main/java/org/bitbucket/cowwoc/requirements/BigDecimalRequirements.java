/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.spi.NumberRequirementsSpi;

/**
 * Verifies requirements of a {@link BigDecimal} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalRequirements
	extends NumberRequirementsSpi<BigDecimalRequirements, BigDecimal>, 
	Isolatable<BigDecimalRequirements>
{
	/**
	 * @return requirements over BigDecimal.precision()
	 */
	BigDecimalPrecisionRequirements precision();

	/**
	 * @return requirements over BigDecimal.scale()
	 */
	BigDecimalScaleRequirements scale();
}
