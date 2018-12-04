/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifiers;
import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;
import org.bitbucket.cowwoc.requirements.internal.java.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.java.util.ExceptionBuilder;

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
	/**
	 * Creates new ComparableCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ComparableCapabilitiesImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isLessThan(T value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThan(String name, T value)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", name, config.toString(value))).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(String name, T value)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("value", value).isNotNull();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
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
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThan(String name, T value)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat("value", value).isNotNull();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", name, config.toString(value))).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(String name, T value)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", value).isNotNull();
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
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("expected", expected).isNotNull();
		if (actual.compareTo(expected) == 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be comparable to %s.", name, expected)).
			addContext("Actual", actual).
			addContext("Expected", expected).
			build();
	}

	@Override
	public S isComparableTo(String name, T expected)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("expected", expected).isNotNull();
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
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("value", value).isNotNull();
		if (actual.compareTo(value) != 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be comparable to %s.", name, value)).
			addContext("Actual", actual).
			addContext("Value", value).
			build();
	}

	@Override
	public S isNotComparableTo(String name, T other)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("value", other).isNotNull();
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
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("startInclusive", startInclusive).isNotNull();
		verifier.requireThat("endExclusive", endExclusive).isNotNull().
			isGreaterThanOrEqualTo("startInclusive", startInclusive);
		if (actual.compareTo(startInclusive) >= 0 && actual.compareTo(endExclusive) < 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be in range [%s, %s).", name, startInclusive, endExclusive)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		JavaVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("startInclusive", startInclusive).isNotNull();
		verifier.requireThat("endInclusive", endInclusive).isNotNull().
			isGreaterThanOrEqualTo("startInclusive", startInclusive);
		if (actual.compareTo(startInclusive) >= 0 && actual.compareTo(endInclusive) <= 0)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be in range [%s, %s].", name, startInclusive, endInclusive)).
			addContext("Actual", actual).
			build();
	}
}
