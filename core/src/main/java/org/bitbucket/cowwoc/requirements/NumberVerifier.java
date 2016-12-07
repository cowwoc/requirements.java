/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.NumberVerifierSpi;

/**
 * Verifier for a {@code Number}.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface NumberVerifier<T extends Number & Comparable<? super T>>
	extends NumberVerifierSpi<NumberVerifier<T>, T>, Isolatable<NumberVerifier<T>>
{
}
