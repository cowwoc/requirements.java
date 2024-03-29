/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

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
	 * @param config         the instance configuration
	 * @param collectionName the name of the collection
	 * @param collection     the collection
	 * @param sizeName       the name of the collection's size
	 * @param size           the size of the collection
	 * @param pluralizer     returns the singular or plural form of the collection's element type
	 * @param failures       the list of validation failures
	 * @param fatalFailure   true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name}, {@code collection},
	 *                        {@code pluralizer} or {@code failures} are null. If {@code name} or
	 *                        {@code collectionName} are blank.
	 */
	public SizeValidatorImpl(ApplicationScope scope, Configuration config, String collectionName,
		Object collection, String sizeName, int size, Pluralizer pluralizer,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, sizeName, size, failures, fatalFailure);
		assert (collectionName != null) : "collectionName may not be null";
		assert (!collectionName.isBlank()) : "collectionName may not be blank";
		assert (collection != null) : "collection may not be null";
		assert (pluralizer != null) : "pluralizer may not be null";
		this.collectionName = collectionName;
		this.collection = collection;
		this.pluralizer = pluralizer;
	}

	@Override
	protected SizeValidator getThis()
	{
		return this;
	}

	@Override
	public SizeValidator isGreaterThanOrEqualTo(Integer value)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual < value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain at least " + config.toString(value) + " " + pluralizer.nameOf(value) +
					".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isGreaterThanOrEqualTo(Integer value, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();
		if (actual < value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain at least " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Minimum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isGreaterThan(Integer value)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual <= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain more than " + config.toString(value) + " " +
					pluralizer.nameOf(value) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isGreaterThan(Integer value, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();
		if (actual <= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain more than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Exclusive minimum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThanOrEqualTo(Integer value)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual > value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " may not contain more than " + config.toString(value) + " " +
					pluralizer.nameOf(value) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThanOrEqualTo(Integer value, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();
		if (actual > value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " may not contain more than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Maximum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThan(Integer value)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		if (actual >= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain less than " + config.toString(value) + " " +
					pluralizer.nameOf(value) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isLessThan(Integer value, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();
		if (actual >= value)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain less than " + name + " " + pluralizer.nameOf(value) + ".").
				addContext("Actual", actual).
				addContext("Exclusive maximum", value);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
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
		if (fatalFailure)
			return this;
		if (actual <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain at least one " + pluralizer.nameOf(1) + ".").
				addContext("Actual", actual);
			addFailure(failure);
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
		if (fatalFailure)
			return this;
		if (actual != 0)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must be empty.").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated collection size can never be negative
	 */
	@Override
	@Deprecated
	public SizeValidator isNotNegative()
	{
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated collection size can never be negative
	 */
	@Override
	@Deprecated
	public SizeValidator isNegative()
	{
		if (fatalFailure)
			return this;
		ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be negative");
		addFailure(failure);
		return this;
	}

	@Override
	public SizeValidator isBetween(Integer startInclusive, Integer endExclusive)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual < startInclusive || actual >= endExclusive)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endExclusive);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain [" + startAsString + ", " + endAsString + ") " +
					pluralizer.nameOf(2) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");
		if (actual < startInclusive || actual > endInclusive)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endInclusive);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain [" + startAsString + ", " + endAsString + "] " +
					pluralizer.nameOf(2) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isEqualTo(Object expected)
	{
		if (fatalFailure)
			return this;
		if (!Objects.equals(actual, expected))
		{
			JavaRequirements verifier = scope.getInternalVerifier();
			List<ValidationFailure> wrongType = verifier.validateThat(expected, "expected").
				isInstanceOf(Integer.class).getFailures();
			if (!wrongType.isEmpty())
			{
				for (ValidationFailure failure : wrongType)
					addFailure(failure);
				return this;
			}
			int expectedAsInt = (Integer) expected;
			String expectedAsString = config.toString(expected);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain " + expectedAsString + " " + pluralizer.nameOf(expectedAsInt) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isEqualTo(Object expected, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (!Objects.equals(actual, expected))
		{
			List<ValidationFailure> wrongType = verifier.validateThat(expected, "expected").
				isInstanceOf(Integer.class).getFailures();
			if (!wrongType.isEmpty())
			{
				for (ValidationFailure failure : wrongType)
					addFailure(failure);
				return this;
			}
			int expectedAsInt = (Integer) expected;
			String expectedAsString = config.toString(expected);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " must contain " + name + " " + pluralizer.nameOf(expectedAsInt) + ".").
				addContext("Actual", actual).
				addContext("Expected", expectedAsString);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotEqualTo(Object other)
	{
		if (fatalFailure)
			return this;
		if (Objects.equals(actual, other))
		{
			int valueAsInt = (Integer) other;
			String valueAsString = config.toString(other);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " may not contain " + valueAsString + " " + pluralizer.nameOf(valueAsInt) + ".").
				addContext("Actual", actual);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator isNotEqualTo(Object other, String name)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (Objects.equals(actual, other))
		{
			int otherAsInt = (Integer) other;
			String otherAsString = config.toString(other);
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				collectionName + " may not contain " + name + " " + pluralizer.nameOf(otherAsInt) + ".").
				addContext("Actual", actual).
				addContext("Unwanted", otherAsString);
			if (actual > 0)
				failure.addContext(collectionName, collection);
			addFailure(failure);
		}
		return this;
	}

	@Override
	@Deprecated
	public SizeValidator isNull()
	{
		if (fatalFailure)
			return this;
		ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be null");
		addFailure(failure);
		return this;
	}
}