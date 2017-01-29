/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * Verifies an {@link int}.
 * <p>
 * @author Gili Tzabari
 */
public class PrimitiveIntegerVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveIntegerVerifier, Integer>
	implements PrimitiveIntegerVerifier
{
	/**
	 * Creates new PrimitiveIntegerVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveIntegerVerifierImpl(ApplicationScope scope, int actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNull()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be null", name), null);
	}
}
