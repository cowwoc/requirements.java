/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.capabilities;

import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

/**
 * Verifies the requirements of a value that extends a primitive number (e.g. {@code int}) but the
 * implementing verifier is not guaranteed to be a {@link PrimitiveNumberVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public interface PrimitiveNumberCapabilities<S, T extends Number & Comparable<? super T>>
	extends NumberCapabilities<S, T>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	S isNull();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	S isNotNull();
}
