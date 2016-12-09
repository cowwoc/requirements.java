/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.spi.ArrayVerifierSpi;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;

/**
 * Verifies an array parameter.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public interface ArrayVerifier<E>
	extends ArrayVerifierSpi<ArrayVerifier<E>, E>, Isolatable<ArrayVerifier<E>>
{
}
