/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@code BooleanVerifier}.
 *
 * @param <T> the type of objects that the value may be compared to
 * @author Gili Tzabari
 */
public final class BooleanVerifierImpl<T extends Comparable<? super T>>
	extends BooleanCapabilitiesImpl<BooleanVerifier>
	implements BooleanVerifier
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
	public BooleanVerifierImpl(ApplicationScope scope, Boolean actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}
}
