/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.NumberVerifierSpi;

/**
 * Verifies a {@link BigDecimal} parameter.
 *
 * @author Gili Tzabari
 */
public interface BigDecimalVerifier
	extends NumberVerifierSpi<BigDecimalVerifier, BigDecimal>, Isolatable<BigDecimalVerifier>
{
	/**
	 * @return verifier for {@code BigDecimal.precision()}
	 */
	BigDecimalPrecisionVerifier precision();

	/**
	 * @return verifier for {@code BigDecimal.scale()}
	 */
	BigDecimalScaleVerifier scale();
}
