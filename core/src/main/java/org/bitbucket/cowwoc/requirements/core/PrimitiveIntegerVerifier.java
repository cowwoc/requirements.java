/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.ext.PrimitiveIntegerVerifierExtension;

/**
 * Verifies an {@link int}.
 *
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerVerifier
	extends PrimitiveIntegerVerifierExtension<PrimitiveIntegerVerifier>,
	Verifier<PrimitiveIntegerVerifier>
{
}
