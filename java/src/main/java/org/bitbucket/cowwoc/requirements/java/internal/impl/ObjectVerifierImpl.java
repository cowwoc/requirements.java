/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of ObjectVerifier.
 *
 * @param <T> the type of the value
 */
public final class ObjectVerifierImpl<T> extends ObjectCapabilitiesImpl<ObjectVerifier<T>, T>
	implements ObjectVerifier<T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected ObjectVerifierImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}
}
