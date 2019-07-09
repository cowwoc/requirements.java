/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected AbstractComparableValidator(ApplicationScope scope, Configuration config, String name, T actual,
	                                      List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	public S isLessThan(T value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference >= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be less than " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference >= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be less than " + name + ".").
				addContext("Actual", actual).
				addContext("Exclusive maximum", value);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference > 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be less than or equal to " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference > 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be less than or equal to " + name + ".").
				addContext("Actual", actual).
				addContext("Maximum", value);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThan(T value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be greater than " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference <= 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be greater than " + name + ".").
				addContext("Actual", actual).
				addContext("Exclusive minimum", value);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference < 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be greater than or equal to " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		int difference = actual.compareTo(value);
		if (difference < 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be greater than or equal to " + name + ".").
				addContext("Actual", actual).
				addContext("Minimum", value);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isComparableTo(T expected)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(expected) != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be comparable to " + config.toString(expected) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(expected) != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be comparable to " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotComparableTo(T value)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(value) == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be comparable to " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(other, "other").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(other) == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not be comparable to " + name + ".").
				addContext("Actual", actual).
				addContext("Other", other);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endExclusive, "endExclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(startInclusive) < 0 || actual.compareTo(endExclusive) >= 0)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endExclusive);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be in range [" + startAsString + ", " + endAsString + ").").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(startInclusive, "startInclusive").isNotNull();
		verifier.requireThat(endInclusive, "endInclusive").isNotNull().
			isGreaterThanOrEqualTo(startInclusive, "startInclusive");

		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.compareTo(startInclusive) < 0 || actual.compareTo(endInclusive) > 0)
		{
			String startAsString = config.toString(startInclusive);
			String endAsString = config.toString(endInclusive);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be in range [" + startAsString + ", " + endAsString + "].").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}
}