/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

/**
 * Verifies the requirements of a value that extends a primitive type (e.g. {@code int}) but the
 * implementing verifier is not guaranteed to be a {@link ExtensibleObjectVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public interface ExtensiblePrimitiveVerifier<S, T>
	extends ExtensibleObjectVerifier<S, T>
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
