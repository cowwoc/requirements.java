/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.NumberVerifierSpi;

/**
 * Verifies a {@link BigDecimal} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalVerifier
	extends NumberVerifierSpi<BigDecimalVerifier, BigDecimal>,
	Isolatable<BigDecimalVerifier>
{
	/**
	 * @return verifier for BigDecimal.precision()
	 */
	BigDecimalPrecisionVerifier precision();

	/**
	 * @return verifier for BigDecimal.scale()
	 */
	BigDecimalScaleVerifier scale();
}
