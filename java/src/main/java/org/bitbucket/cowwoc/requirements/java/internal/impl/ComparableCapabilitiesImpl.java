/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link ComparableCapabilities}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class ComparableCapabilitiesImpl<S, T extends Comparable<? super T>>
	extends ObjectCapabilitiesImpl<S, T>
	implements ComparableCapabilities<S, T>
{
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected ComparableCapabilitiesImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isLessThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, valueAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Exclusive maximum", value).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", name, valueAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public S isGreaterThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", name, valueAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Exclusive minimum", value).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", name, valueAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public S isComparableTo(T expected)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		if (actual.compareTo(expected) == 0)
			return getThis();
		String expectedAsString = secretConfiguration.toString(config, expected);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be comparable to %s.", name, expectedAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual.compareTo(expected) == 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be comparable to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Expected", expected).
			build();
	}

	@Override
	public S isNotComparableTo(T value)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual.compareTo(value) != 0)
			return getThis();
		String valueAsString = secretConfiguration.toString(config, value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be comparable to %s.", name, valueAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(other, "other").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual.compareTo(other) != 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be comparable to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Other", other).
			build();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual.compareTo(startInclusive) >= 0 && actual.compareTo(endExclusive) < 0)
			return getThis();
		String startAsString = secretConfiguration.toString(config, startInclusive);
		String endAsString = secretConfiguration.toString(config, endExclusive);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be in range [%s, %s).", name, startAsString, endAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual.compareTo(startInclusive) >= 0 && actual.compareTo(endInclusive) <= 0)
			return getThis();
		String startAsString = secretConfiguration.toString(config, startInclusive);
		String endAsString = secretConfiguration.toString(config, endInclusive);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be in range [%s, %s].", name, startAsString, endAsString)).
			addContext("Actual", actual).
			build();
	}
}
