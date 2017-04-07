/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of ComparableVerifier.
 *
 * @param <T> the type of objects that the value may be compared to
 * @author Gili Tzabari
 */
public final class ComparableVerifierImpl<T extends Comparable<? super T>>
	extends ComparableCapabilitiesImpl<ComparableVerifier<T>, T>
	implements ComparableVerifier<T>
{
	/**
	 * Creates new ComparableVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ComparableVerifierImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}
}
