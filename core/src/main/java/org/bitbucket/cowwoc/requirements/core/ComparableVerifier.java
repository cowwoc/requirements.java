/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.spi.ComparableVerifierSpi;
import org.bitbucket.cowwoc.requirements.core.spi.Isolatable;

/**
 * Verifies a {@link Comparable}.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ComparableVerifier<T extends Comparable<? super T>>
	extends ComparableVerifierSpi<ComparableVerifier<T>, T>, Isolatable<ComparableVerifier<T>>
{
}