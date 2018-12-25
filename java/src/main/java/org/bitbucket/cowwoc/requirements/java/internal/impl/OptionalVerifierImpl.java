/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.util.Optional;

/**
 * Default implementation of OptionalVerifier.
 */
public final class OptionalVerifierImpl
	extends ObjectCapabilitiesImpl<OptionalVerifier, Optional<?>>
	implements OptionalVerifier
{
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected OptionalVerifierImpl(ApplicationScope scope, String name, Optional<?> actual, Configuration config)
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
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain %s.", name, valueAsString)).
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
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Expected", expectedAsOptional).
			build();
	}
}
