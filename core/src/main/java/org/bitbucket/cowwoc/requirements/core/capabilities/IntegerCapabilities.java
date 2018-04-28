/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.IntegerVerifier;

/**
 * Verifies an integer value but the implementing verifier is not guaranteed to be a {@link IntegerVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public interface IntegerCapabilities<S, T extends Number & Comparable<? super T>>
	extends NumberCapabilities<S, T>
{

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always a whole number
	 */
	@Override
	@Deprecated
	S isWholeNumber();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always a whole number
	 */
	@Override
	@Deprecated
	S isNotWholeNumber();
}
