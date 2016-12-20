/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies an {@link Object} parameter.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectVerifier<T>
	extends ObjectVerifierExtension<ObjectVerifier<T>, T>, Verifier<ObjectVerifier<T>>
{
}
