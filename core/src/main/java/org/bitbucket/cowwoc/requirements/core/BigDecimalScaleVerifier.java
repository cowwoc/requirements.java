/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.ext.PrimitiveIntegerVerifierExtension;

/**
 * Verifies a {@link BigDecimal#scale()}.
 *
 * @author Gili Tzabari
 */
public interface BigDecimalScaleVerifier
	extends PrimitiveIntegerVerifierExtension<BigDecimalScaleVerifier>,
	Verifier<BigDecimalScaleVerifier>
{
}
