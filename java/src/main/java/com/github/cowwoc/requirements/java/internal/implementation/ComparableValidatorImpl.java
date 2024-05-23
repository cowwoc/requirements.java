/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaValidators;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.ComparableValidator;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the value that is being validated
 */
public final class ComparableValidatorImpl<T extends Comparable<T>>
	extends AbstractObjectValidator<ComparableValidator<T>, T>
	implements ComparableValidator<T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ComparableValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public ComparableValidator<T> isLessThan(T maximumExclusive)
	{
		scope.getInternalValidators().requireThat(maximumExclusive, "maximumExclusive").isNotNull();
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public ComparableValidator<T> isLessThan(T maximumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumExclusive, "maximumExclusive").isNotNull();
		return isLessThanImpl(maximumExclusive, name);
	}

	private ComparableValidator<T> isLessThanImpl(T maximumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(maximumExclusive) >= 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public ComparableValidator<T> isLessThanOrEqualTo(T maximumInclusive)
	{
		scope.getInternalValidators().requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public ComparableValidator<T> isLessThanOrEqualTo(T maximumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private ComparableValidator<T> isLessThanOrEqualToImpl(T maximumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name,
					maximumInclusive).toString());
		}
		else if (value.compareTo(maximumInclusive) > 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name,
					maximumInclusive).toString());
		}
		return this;
	}

	@Override
	public ComparableValidator<T> isGreaterThanOrEqualTo(T minimumInclusive)
	{
		scope.getInternalValidators().requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public ComparableValidator<T> isGreaterThanOrEqualTo(T minimumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private ComparableValidator<T> isGreaterThanOrEqualToImpl(T minimumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name,
					minimumInclusive).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(minimumInclusive) < 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name,
					minimumInclusive).toString());
		}
		return this;
	}

	@Override
	public ComparableValidator<T> isGreaterThan(T minimumExclusive)
	{
		scope.getInternalValidators().requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public ComparableValidator<T> isGreaterThan(T minimumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private ComparableValidator<T> isGreaterThanImpl(T minimumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, null, name, minimumExclusive).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(minimumExclusive) <= 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public ComparableValidator<T> isBetween(T minimumInclusive, T maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public ComparableValidator<T> isBetween(T minimum, boolean minimumInclusive, T maximum,
		boolean maximumInclusive)
	{
		JavaValidators internalValidators = scope.getInternalValidators();
		internalValidators.requireThat(minimum, "minimum").isNotNull();
		internalValidators.requireThat(maximum, "maximum").isNotNull();
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(T minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value.compareTo(minimum) < 0;
		return value.compareTo(minimum) <= 0;
	}

	private boolean maximumFailed(T maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value.compareTo(maximum) > 0;
		return value.compareTo(maximum) >= 0;
	}
}