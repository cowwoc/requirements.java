/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.NumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@link NumberVerifier}.
 *
 * @param <T> the type of the value
 */
public final class NumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends NumberCapabilitiesImpl<NumberVerifier<T>, T>
	implements NumberVerifier<T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public NumberVerifierImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}
}