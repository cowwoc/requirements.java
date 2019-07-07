/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;

/**
 * Verifies the requirements of an integer value but the implementing verifier is not guaranteed to be a
 * {@link IntegerVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public interface ExtensibleIntegerVerifier<S, T extends Number & Comparable<? super T>>
	extends ExtensibleNumberVerifier<S, T>
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
