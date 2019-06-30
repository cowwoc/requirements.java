/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Objects;

/**
 * Default implementation of a {@code SizeVerifier}.
 */
public final class SizeVerifierImpl
	extends AbstractNumberVerifier<SizeVerifier, Integer>
	implements SizeVerifier
{
	private final String collectionName;
	private final Object collection;
	private final Pluralizer pluralizer;

	/**
	 * @param scope          the application configuration
	 * @param collectionName the name of the collection
	 * @param collection     the collection
	 * @param sizeName       the name of the collection's size
	 * @param size           the size of the collection
	 * @param pluralizer     returns the singular or plural form of the collection's element type
	 * @param config         the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name}, {@code collection} or {@code config} are null.
	 *                        If {@code name} is empty.
	 */
	public SizeVerifierImpl(ApplicationScope scope, String collectionName, Object collection,
	                        String sizeName, int size, Pluralizer pluralizer, Configuration config)
	{
		super(scope, sizeName, size, config);
		assert (collectionName != null) : "collectionName may not be null";
		assert (!collectionName.isEmpty()) : "collectionName may not be empty";
		assert (collection != null) : "collection may not be null";
		assert (pluralizer != null) : "pluralizer may not be null";
		this.collectionName = collectionName;
		this.collection = collection;
		this.pluralizer = pluralizer;
	}

	@Override
	public SizeVerifier isGreaterThanOrEqualTo(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual >= value)
			return getThis();
		String valueAsString = config.toString(value);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain at least " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual >= value)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain at least " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Minimum", value);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual > value)
			return getThis();
		String valueAsString = config.toString(value);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain more than " + valueAsString + " " + pluralizer.nameOf(value)).
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual > value)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain more than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Exclusive minimum", value);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual <= value)
			return getThis();
		String valueAsString = config.toString(value);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " may not contain more than " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual <= value)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " may not contain more than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Maximum", value);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isLessThan(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual < value)
			return getThis();
		String valueAsString = config.toString(value);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain less than " + valueAsString + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isLessThan(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual < value)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain less than " + name + " " + pluralizer.nameOf(value) + ".").
			addContext("Actual", actual).
			addContext("Exclusive maximum", value);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isNotPositive()
	{
		return isZero();
	}

	@Override
	public SizeVerifier isPositive()
	{
		if (actual > 0)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain at least one " + pluralizer.nameOf(1) + ".").
			addContext("Actual", actual);
		throw eb.build();
	}

	@Override
	public SizeVerifier isNotZero()
	{
		return isPositive();
	}

	@Override
	public SizeVerifier isZero()
	{
		if (actual == 0)
			return getThis();
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must be empty.").
			addContext("Actual", collection);
		throw eb.build();
	}

	@Deprecated
	@Override
	public SizeVerifier isNotNegative()
	{
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " can never be negative").
			build();
	}

	@Deprecated
	@Override
	public SizeVerifier isNegative()
	{
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " can never be negative").
			build();
	}

	@Override
	public SizeVerifier isBetween(Integer startInclusive, Integer endExclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual >= startInclusive && actual < endExclusive)
			return getThis();
		String startAsString = config.toString(startInclusive);
		String endAsString = config.toString(endExclusive);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain [" + startAsString + ", " + endAsString + ") " + pluralizer.nameOf(2) +
				".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual >= startInclusive && actual <= endInclusive)
			return getThis();
		String startAsString = config.toString(startInclusive);
		String endAsString = config.toString(endInclusive);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain [" + startAsString + ", " + endAsString + "] " + pluralizer.nameOf(2) +
				".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isEqualTo(Object expected)
	{
		if (Objects.equals(actual, expected))
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isInstanceOf(Integer.class);
		int expectedAsInt = (Integer) expected;
		String expectedAsString = config.toString(expected);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain " + expectedAsString + " " + pluralizer.nameOf(expectedAsInt) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isEqualTo(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, expected))
			return getThis();
		verifier.requireThat(expected, "expected").isInstanceOf(Integer.class);
		int expectedAsInt = (Integer) expected;
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " must contain " + name + " " + pluralizer.nameOf(expectedAsInt) + ".").
			addContext("Actual", actual).
			addContext("Expected", expected);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isNotEqualTo(Object value)
	{
		if (!Objects.equals(actual, value))
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isInstanceOf(Integer.class);
		int valueAsInt = (Integer) value;
		String valueAsString = config.toString(value);
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " may not contain " + valueAsString + " " + pluralizer.nameOf(valueAsInt) + ".").
			addContext("Actual", actual);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	public SizeVerifier isNotEqualTo(Object value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, value))
			return getThis();
		verifier.requireThat(value, "value").isInstanceOf(Integer.class);
		int valueAsInt = (Integer) value;
		ExceptionBuilder<IllegalArgumentException> eb = new ExceptionBuilder<>(scope, config,
			IllegalArgumentException.class,
			collectionName + " may not contain " + name + " " + pluralizer.nameOf(valueAsInt) + ".").
			addContext("Actual", actual).
			addContext("Unwanted", value);
		if (actual > 0)
			eb.addContext(collectionName, collection);
		throw eb.build();
	}

	@Override
	@Deprecated
	public SizeVerifier isNull()
	{
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " can never be null").
			build();
	}
}
