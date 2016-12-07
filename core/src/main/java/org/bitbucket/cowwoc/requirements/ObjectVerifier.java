/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.ObjectVerifierSpi;

/**
 * Verifies an {@link Object} parameter.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectVerifier<T>
	extends ObjectVerifierSpi<ObjectVerifier<T>, T>, Isolatable<ObjectVerifier<T>>
{
}
