/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;

/**
 * Default implementation of ObjectVerifier.
 * <p>
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class ObjectVerifierImpl<T> extends ObjectCapabilitiesImpl<ObjectVerifier<T>, T>
	implements ObjectVerifier<T>
{
	/**
	 * Creates new ObjectVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ObjectVerifierImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}
}