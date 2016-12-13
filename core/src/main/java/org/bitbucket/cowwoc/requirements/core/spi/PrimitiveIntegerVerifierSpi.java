/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

/**
 * Verifies an {@link int} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerVerifierSpi<S extends PrimitiveIntegerVerifierSpi<S>>
	extends NumberVerifierSpi<S, Integer>
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