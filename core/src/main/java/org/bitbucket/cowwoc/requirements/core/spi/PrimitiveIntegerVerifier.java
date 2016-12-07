/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

/**
 * Verifies an {@link int} parameter.
 *
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerVerifier
	extends PrimitiveIntegerVerifierSpi<PrimitiveIntegerVerifier>,
	Isolatable<PrimitiveIntegerVerifier>
{
}
