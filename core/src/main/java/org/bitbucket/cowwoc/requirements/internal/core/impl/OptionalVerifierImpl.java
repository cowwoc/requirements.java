/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of OptionalVerifier.
 *
 * @author Gili Tzabari
 */
public final class OptionalVerifierImpl
	extends ObjectCapabilitiesImpl<OptionalVerifier, Optional<?>>
	implements OptionalVerifier
{
	/**
	 * Creates new OptionalVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
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
			String.format("%s must be present", name)).
			build();
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		if (!actual.isPresent())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
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
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public OptionalVerifier contains(String name, Object expected)
	{
		Optional<?> expectedOptional = Optional.ofNullable(expected);
		if (actual.equals(expectedOptional))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Expected", expectedOptional).
			build();
	}
}
