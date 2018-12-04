/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;

/**
 * Verifies a value that extends a primitive number (e.g. {@code int}) but the implementing verifier
 * is not guaranteed to be a {@link PrimitiveNumberVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public interface PrimitiveIntegerCapabilities<S, T extends Number & Comparable<? super T>>
	extends IntegerCapabilities<S, T>, PrimitiveNumberCapabilities<S, T>
{
}
