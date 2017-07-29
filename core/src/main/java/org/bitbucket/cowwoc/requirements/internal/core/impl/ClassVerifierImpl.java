/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link ClassVerifier}.
 *
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public final class ClassVerifierImpl<T> extends ObjectCapabilitiesImpl<ClassVerifier<T>, Class<T>>
	implements ClassVerifier<T>
{
	/**
	 * Creates new ClassVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ClassVerifierImpl(ApplicationScope scope, String name, Class<T> actual,
		Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat("type", type).isNotNull();
		if (actual.isAssignableFrom(type))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a supertype of %s.", name, type)).
			addContext("Actual", actual.getClass()).
			build();
	}
}
