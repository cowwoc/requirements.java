/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

/**
 * Verifies the requirements of a value that extends a primitive number (e.g. {@code int}) but the
 * implementing verifier is not guaranteed to be a {@link PrimitiveNumberVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public interface ExtensiblePrimitiveNumberVerifier<S, T extends Number & Comparable<? super T>>
	extends ExtensibleNumberVerifier<S, T>
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
