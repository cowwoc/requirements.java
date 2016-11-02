/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

/**
 * Verifies requirements of an {@link int} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @author Gili Tzabari
 */
public interface PrimitiveIntegerRequirementsSpi<S extends PrimitiveIntegerRequirementsSpi<S>>
	extends NumberRequirementsSpi<S, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the parameter cannot be null
	 */
	@Deprecated
	@Override
	S isNull();
}
