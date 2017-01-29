/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Default implementation of {@code NumberVerifier}.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends NumberCapabilitiesImpl<NumberVerifier<T>, T>
	implements NumberVerifier<T>
{
	/**
	 * Creates a new NumberVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public NumberVerifierImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}
}
