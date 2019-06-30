/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveCharacterVerifier}.
 */
public final class PrimitiveCharacterVerifierImpl
	extends AbstractComparableVerifier<PrimitiveCharacterVerifier, Character>
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

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
