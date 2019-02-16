/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
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
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
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
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			this.name + " must be less than " + valueAsString + ".").
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
			this.name + " must be less than " + name + ".").
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
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be less than or equal to " + valueAsString + ".").
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
			this.name + " must be less than or equal to " + name + ".").
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
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be greater than " + valueAsString + ".").
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
			this.name + " must be greater than " + name + ".").
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
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be greater than or equal to " + valueAsString + ".").
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
			this.name + " must be greater than or equal to " + name + ".").
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
		String expectedAsString = config.toString(expected);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be comparable to " + expectedAsString + ".").
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
			this.name + " must be comparable to " + name + ".").
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
		String valueAsString = config.toString(value);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be comparable to " + valueAsString + ".").
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
			this.name + " may not be comparable to " + name + ".").
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
		String startAsString = config.toString(startInclusive);
		String endAsString = config.toString(endExclusive);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be in range [" + startAsString + ", " + endAsString + ").").
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
		String startAsString = config.toString(startInclusive);
		String endAsString = config.toString(endInclusive);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be in range [" + startAsString + ", " + endAsString + "].").
			addContext("Actual", actual).
			build();
	}
}
