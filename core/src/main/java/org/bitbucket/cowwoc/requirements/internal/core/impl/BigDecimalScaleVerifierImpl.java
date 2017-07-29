/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * An implementation of {@link PrimitiveNumberVerifier} for a {@link BigDecimal}'s scale.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalScaleVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<Integer>, Integer>
	implements PrimitiveNumberVerifier<Integer>
{
	/**
	 * Creates new BigDecimalScaleVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config}
	 *                        are null; if {@code name} is empty
	 */
	public BigDecimalScaleVerifierImpl(ApplicationScope scope, String name, BigDecimal actual,
		Configuration config)
	{
		super(scope, name + ".scale()", actual.scale(), config);
	}
}
