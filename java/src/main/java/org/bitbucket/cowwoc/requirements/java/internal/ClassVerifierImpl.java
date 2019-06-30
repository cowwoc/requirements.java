/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Default implementation of {@code ClassVerifier}.
 *
 * @param <T> the type of the class
 */
public final class ClassVerifierImpl<T> extends ObjectCapabilitiesImpl<ClassVerifier<T>, Class<T>>
	implements ClassVerifier<T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public ClassVerifierImpl(ApplicationScope scope, String name, Class<T> actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (actual.isAssignableFrom(type))
			return this;
		String actualAsString = config.toString(actual);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be a supertype of " + type + ".").
			addContext("Actual", actualAsString).
			build();
	}
}
