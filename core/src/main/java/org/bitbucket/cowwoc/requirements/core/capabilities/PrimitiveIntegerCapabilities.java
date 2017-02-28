/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;

/**
 * Verifies a value that extends {@code int} but the implementing verifier is not guaranteed
 * to be a {@link PrimitiveIntegerVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerCapabilities<S>
	extends NumberCapabilities<S, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Deprecated
	@Override
	S isNull();
}
