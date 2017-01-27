/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.ext;

/**
 * Verifies an {@link int}.
 *
 * @param <S> the type of verifier that methods should return
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerVerifierExtension<S extends PrimitiveIntegerVerifierExtension<S>>
	extends NumberVerifierExtension<S, Integer>
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
