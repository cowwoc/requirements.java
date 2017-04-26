/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@code BooleanVerifier}.
 *
 * @author Gili Tzabari
 */
public final class PrimitiveBooleanVerifierImpl
	extends BooleanCapabilitiesImpl<PrimitiveBooleanVerifier>
	implements PrimitiveBooleanVerifier
{
	/**
	 * Creates new BooleanVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveBooleanVerifierImpl(ApplicationScope scope, boolean actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Deprecated
	@Override
	public PrimitiveBooleanVerifier isNotNull()
	{
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveBooleanVerifier isNull()
	{
		return super.isNull();
	}
}
