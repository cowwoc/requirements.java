/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveCharacterVerifier}.
 */
public final class PrimitiveCharacterVerifierImpl
	extends ComparableCapabilitiesImpl<PrimitiveCharacterVerifier, Character>
	implements PrimitiveCharacterVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveCharacterVerifierImpl(ApplicationScope scope, String name, char actual,
	                                      Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNotNull()
	{
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNull()
	{
		return super.isNull();
	}
}