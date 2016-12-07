/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.core.spi.PrimitiveIntegerVerifierSpi;

/**
 * Verifies a {@link BigDecimal#scale()}.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalScaleVerifier
	extends PrimitiveIntegerVerifierSpi<BigDecimalScaleVerifier>, 
	Isolatable<BigDecimalScaleVerifier>
{
}
