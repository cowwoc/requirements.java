/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.List;
import java.util.Objects;

/**
 * Default implementation of a {@code SizeValidator}.
 */
public final class SizeValidatorImpl
	extends AbstractNumberValidator<SizeValidator, Integer>
	implements SizeValidator
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
	 * @param failures       the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code collection}, {@code config} or
	 *                        {@code failures} are null. If {@code name} is empty.
	 */
	public SizeValidatorImpl(ApplicationScope scope, String collectionName, Object collection,
	                         String sizeName, int size, Pluralizer pluralizer, Configuration config,
	                         List<ValidationFailure> failures)
	{
		super(scope, sizeName, size, config, failures);
		assert (collectionName != null) : "collectionName may not be null";
		assert (!collectionName.isEmpty()) : "collectionName may not be empty";
		assert (collection != null) : "collection may not be null";
		assert (pluralizer != null) : "pluralizer may not be null";
		this.collectionName = collectionName;
		this.collection = collection;
		this.pluralizer = pluralizer;
	}

	@Override
	public SizeValidator isGreaterThanOrEqualTo(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual < value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain at least " + config.toString(value) + " " + pluralizer.nameOf(value) +
					".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public SizeValidator isGreaterThanOrEqualTo(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual < value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain at least " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Minimum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isGreaterThan(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual <= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain more than " + config.toString(value) + " " +
					pluralizer.nameOf(value)).
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isGreaterThan(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual <= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain more than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Exclusive minimum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThanOrEqualTo(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual > value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " may not contain more than " + config.toString(value) + " " +
					pluralizer.nameOf(value) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThanOrEqualTo(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual > value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " may not contain more than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Maximum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThan(Integer value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual >= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain less than " + config.toString(value) + " " +
					pluralizer.nameOf(value) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThan(Integer value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual >= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain less than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Exclusive maximum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotPositive()
	{
		return isZero();
	}

	@Override
	public SizeValidator isPositive()
	{
		if (actual <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain at least one " + pluralizer.nameOf(1) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotZero()
	{
		return isPositive();
	}

	@Override
	public SizeValidator isZero()
	{
		if (actual != 0)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must be empty.").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated collection size can never be negative
	 */
	@Deprecated
	@Override
	public SizeValidator isNotNegative()
	{
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated collection size can never be negative
	 */
	@Deprecated
	@Override
	public SizeValidator isNegative()
	{
		ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " can never be negative");
		failures.add(failure);
		return this;
	}

	@Override
	public SizeValidator isBetween(Integer startInclusive, Integer endExclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual < startInclusive || actual >= endExclusive)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endExclusive);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain [" + startAsString + ", " + endAsString + ") " +
					pluralizer.nameOf(2) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual < startInclusive && actual > endInclusive)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endInclusive);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain [" + startAsString + ", " + endAsString + "] " +
					pluralizer.nameOf(2) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isEqualTo(Object expected)
	{
		if (!Objects.equals(actual, expected))
		{
			JavaRequirements verifier = scope.getInternalVerifier();
			List<ValidationFailure> wrongType = verifier.validateThat(expected, "expected").
				isInstanceOf(Integer.class).getFailures();
			if (!wrongType.isEmpty())
			{
				failures.addAll(wrongType);
				return this;
			}
			int expectedAsInt = (Integer) expected;
			String expectedAsString = config.toString(expected);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain " + expectedAsString + " " + pluralizer.nameOf(expectedAsInt) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isEqualTo(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, expected))
		{
			List<ValidationFailure> wrongType = verifier.validateThat(expected, "expected").
				isInstanceOf(Integer.class).getFailures();
			if (!wrongType.isEmpty())
			{
				failures.addAll(wrongType);
				return this;
			}
			int expectedAsInt = (Integer) expected;
			String expectedAsString = config.toString(expected);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " must contain " + name + " " + pluralizer.nameOf(expectedAsInt) + ".").
				addContext("Actual", actual).
				addContext("Expected", expected);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotEqualTo(Object value)
	{
		if (Objects.equals(actual, value))
		{
			JavaRequirements verifier = scope.getInternalVerifier();
			int valueAsInt = (Integer) value;
			String valueAsString = config.toString(value);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " may not contain " + valueAsString + " " + pluralizer.nameOf(valueAsInt) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotEqualTo(Object value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, value))
		{
			int valueAsInt = (Integer) value;
			String valueAsString = config.toString(value);
			ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
				collectionName + " may not contain " + name + " " + pluralizer.nameOf(valueAsInt) + ".").
				addContext("Actual", actual).
				addContext("Unwanted", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			failures.add(failure);
		}
		return this;
	}

	@Override
	@Deprecated
	public SizeValidator isNull()
	{
		ValidationFailureImpl failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " can never be null");
		failures.add(failure);
		return this;
	}
}
