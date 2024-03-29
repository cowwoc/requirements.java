/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An implementation of {@code ExtensibleComparableValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractComparableValidator<S, T extends Comparable<? super T>>
	extends AbstractObjectValidator<S, T>
	implements ExtensibleComparableValidator<S, T>
{
	/**
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	protected AbstractComparableValidator(ApplicationScope scope, Configuration config, String name, T actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	public S isLessThan(T value)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference >= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be less than " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference >= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be less than " + name + ".").
				addContext("Actual", actual).
				addContext("Exclusive maximum", value);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference > 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be less than or equal to " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference > 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be less than or equal to " + name + ".").
				addContext("Actual", actual).
				addContext("Maximum", value);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThan(T value)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be greater than " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be greater than " + name + ".").
				addContext("Actual", actual).
				addContext("Exclusive minimum", value);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference < 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be greater than or equal to " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		int difference = actual.compareTo(value);
		if (difference < 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be greater than or equal to " + name + ".").
				addContext("Actual", actual).
				addContext("Minimum", value);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isComparableTo(T expected)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(expected) != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be comparable to " + config.toString(expected) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(expected) != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be comparable to " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotComparableTo(T value)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(value) == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be comparable to " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(other, "other").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(other) == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not be comparable to " + name + ".").
				addContext("Actual", actual).
				addContext("Other", other);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(startInclusive) < 0 || actual.compareTo(endExclusive) >= 0)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endExclusive);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be in range [" + startAsString + ", " + endAsString + ").").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.compareTo(startInclusive) < 0 || actual.compareTo(endInclusive) > 0)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endInclusive);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be in range [" + startAsString + ", " + endAsString + "].").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}
}