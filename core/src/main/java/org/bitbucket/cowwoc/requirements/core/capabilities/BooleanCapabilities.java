/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.BooleanVerifier;

/**
 * Verifies a boolean value but the implementing verifier is not guaranteed to be a
 * {@link BooleanVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 */
public interface BooleanCapabilities<S> extends ComparableCapabilities<S, Boolean>
{
	/**
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not true
	 */
	S isTrue();

	/**
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not false
	 */
	S isFalse();
}
