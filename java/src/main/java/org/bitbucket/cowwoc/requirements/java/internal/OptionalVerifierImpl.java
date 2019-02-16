/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.util.Optional;

/**
 * Default implementation of OptionalVerifier.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalVerifierImpl
	extends ObjectCapabilitiesImpl<OptionalVerifier, Optional<?>>
	implements OptionalVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public OptionalVerifierImpl(ApplicationScope scope, String name, Optional<?> actual,
	                            Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public OptionalVerifier isPresent()
	{
		if (actual.isPresent())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be present").
			build();
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be empty.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		if (value == null)
			return isEmpty();
		Optional<?> expected = Optional.of(value);
		if (actual.equals(expected))
			return this;
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must contain " + valueAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public OptionalVerifier contains(Object expected, String name)
	{
		Optional<?> expectedAsOptional = Optional.ofNullable(expected);
		if (actual.equals(expectedAsOptional))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			this.name + " must contain " + name + ".").
			addContext("Actual", actual).
			addContext("Expected", expectedAsOptional).
			build();
	}
}
