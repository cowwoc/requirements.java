/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.message.CollectionMessages;
import io.github.cowwoc.requirements13.java.internal.message.ComparableMessages;
import io.github.cowwoc.requirements13.java.internal.message.NumberMessages;
import io.github.cowwoc.requirements13.java.internal.message.ObjectMessages;
import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.Numbers;
import io.github.cowwoc.requirements13.java.internal.util.Pluralizer;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Validates the state of an object's size.
 */
public final class ObjectSizeValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveUnsignedIntegerValidator, Integer>
	implements PrimitiveUnsignedIntegerValidator
{
	private final AbstractObjectValidator<?, ?> objectValidator;
	private final Pluralizer pluralizer;
	private final Comparables<PrimitiveUnsignedIntegerValidator, Integer> comparables =
		new Comparables<>(this);

	/**
	 * @param scope           the application configuration
	 * @param configuration   the validator configuration
	 * @param objectValidator the object's validator
	 * @param sizeName        the name of the object's size
	 * @param size            the object's size
	 * @param pluralizer      the type of elements in the object
	 * @param context         the contextual information set by a parent validator or the user
	 * @param failures        the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public ObjectSizeValidatorImpl(ApplicationScope scope, Configuration configuration,
		AbstractObjectValidator<?, ?> objectValidator, String sizeName, ValidationTarget<Integer> size,
		Pluralizer pluralizer, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, sizeName, size, context, failures);

		assert objectValidator != null;
		assert pluralizer != null;

		this.objectValidator = objectValidator;
		this.pluralizer = pluralizer;
	}

	@Override
	public int getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public int getValueOrDefault(int defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	private PrimitiveUnsignedIntegerValidator isEqualToImpl(int expected, String name)
	{
		if (value.validationFailed(v -> v == expected))
		{
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null), "must contain",
					name, expected, pluralizer).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveUnsignedIntegerValidator isNotEqualToImpl(int unwanted, String name)
	{
		if (value.validationFailed(v -> v != unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null), "may not contain",
					name, unwanted, pluralizer).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isZero()
	{
		if (value.validationFailed(v -> v == 0))
		{
			failOnNull();
			addIllegalArgumentException(ObjectMessages.isEmptyFailed(objectValidator).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotZero()
	{
		if (value.validationFailed(v -> v != 0))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.isNotEmptyFailed(objectValidator).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isPositive()
	{
		return isNotZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotPositive()
	{
		return isZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumExclusive, "maximumExclusive").isNotNull();
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isLessThanImpl(Integer maximumExclusive, String name)
	{
		if (value.validationFailed(v -> v < maximumExclusive))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null),
					"must contain less than", name, maximumExclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		scope.getInternalValidators().requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isLessThanOrEqualToImpl(Integer maximumInclusive, String name)
	{
		if (value.validationFailed(v -> v <= maximumInclusive))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null),
					"may not contain more than", name, maximumInclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		scope.getInternalValidators().requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualToImpl(Integer minimumInclusive, String name)
	{
		if (value.validationFailed(v -> v >= minimumInclusive))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null),
					"must contain at least", name, minimumInclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		scope.getInternalValidators().requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanImpl(Integer minimumExclusive, String name)
	{
		if (value.validationFailed(v -> v > minimumExclusive))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsSizeFailed(objectValidator, this.name, value.or(null),
					"must contain more than", name, minimumExclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimum, boolean minimumIsInclusive,
		Integer maximum, boolean maximumIsInclusive)
	{
		return isBetween((int) minimum, minimumIsInclusive, (int) maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor, String name)
	{
		requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveUnsignedIntegerValidator isMultipleOfImpl(int factor, String name)
	{
		if (value.validationFailed(v -> Numbers.isMultipleOf(v, factor)))
		{
			failOnNull();
			MessageBuilder messageBuilder = NumberMessages.isMultipleOfFailed(this, name, factor);
			objectValidator.value.ifValid(v -> messageBuilder.withContext(v, objectValidator.getName()));
			addIllegalArgumentException(messageBuilder.toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor, String name)
	{
		requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveUnsignedIntegerValidator isNotMultipleOfImpl(int factor, String name)
	{
		if (value.validationFailed(v -> !Numbers.isMultipleOf(v, factor)))
		{
			failOnNull();
			MessageBuilder messageBuilder = NumberMessages.isNotMultipleOfFailed(this, name, factor);
			objectValidator.value.ifValid(v -> messageBuilder.withContext(v, objectValidator.getName()));
			addIllegalArgumentException(messageBuilder.toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanImpl(maximumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanImpl(minimumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimum, boolean minimumIsInclusive, int maximum,
		boolean maximumIsInclusive)
	{
		scope.getInternalValidators().requireThat(minimum, "minimum").isLessThanOrEqualTo(maximum, "maximum");
		if (value.validationFailed(v ->
			comparables.inBounds(v, minimum, minimumIsInclusive, maximum, maximumIsInclusive)))
		{
			failOnNull();
			addIllegalArgumentException(
				ComparableMessages.isBetweenFailed(this, minimum, minimumIsInclusive, maximum, maximumIsInclusive).
					toString());
		}
		return this;
	}
}