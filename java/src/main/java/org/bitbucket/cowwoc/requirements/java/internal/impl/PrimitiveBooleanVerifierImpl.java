/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveBooleanVerifier}.
 */
public final class PrimitiveBooleanVerifierImpl
	extends BooleanCapabilitiesImpl<PrimitiveBooleanVerifier>
	implements PrimitiveBooleanVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected PrimitiveBooleanVerifierImpl(ApplicationScope scope, String name, boolean actual,
	                                       Configuration config)
	{
		super(scope, name, actual, config);
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
