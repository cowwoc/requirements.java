/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import java.math.BigDecimal;

/**
 * Verifies the requirements of a {@link BigDecimal#precision()}.
 */
public interface ContainerSizeVerifier extends PrimitiveNumberVerifier<Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	PrimitiveNumberVerifier<Integer> isNegative();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	PrimitiveNumberVerifier<Integer> isNotNegative();
}
