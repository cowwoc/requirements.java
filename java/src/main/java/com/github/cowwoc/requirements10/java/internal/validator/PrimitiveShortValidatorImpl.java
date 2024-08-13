package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveShortValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveShortValidator, Short>
	implements PrimitiveShortValidator
{
	private final Shorts<PrimitiveShortValidator> shorts = new Shorts<>(this);

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public PrimitiveShortValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Short> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public short getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public short getValueOrDefault(short defaultValue)
	{
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveShortValidator isEqualTo(short expected)
	{
		return shorts.isEqualTo(expected);
	}

	@Override
	public PrimitiveShortValidator isEqualTo(short expected, String name)
	{
		return shorts.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveShortValidator isNotEqualTo(short unwanted)
	{
		return shorts.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveShortValidator isNotEqualTo(short unwanted, String name)
	{
		return shorts.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveShortValidator isNegative()
	{
		return shorts.isNegative();
	}

	@Override
	public PrimitiveShortValidator isNotNegative()
	{
		return shorts.isNotNegative();
	}

	@Override
	public PrimitiveShortValidator isZero()
	{
		return shorts.isZero();
	}

	@Override
	public PrimitiveShortValidator isNotZero()
	{
		return shorts.isNotZero();
	}

	@Override
	public PrimitiveShortValidator isPositive()
	{
		return shorts.isPositive();
	}

	@Override
	public PrimitiveShortValidator isNotPositive()
	{
		return shorts.isNotPositive();
	}

	@Override
	public PrimitiveShortValidator isLessThan(short maximumExclusive)
	{
		return shorts.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveShortValidator isLessThan(short maximumExclusive, String name)
	{
		return shorts.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive, String name)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive, String name)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveShortValidator isGreaterThan(short minimumExclusive)
	{
		return shorts.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveShortValidator isGreaterThan(short minimumExclusive, String name)
	{
		return shorts.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveShortValidator isBetween(short minimumInclusive, short maximumExclusive)
	{
		return shorts.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveShortValidator isBetween(short minimum, boolean minimumIsInclusive, short maximum,
		boolean maximumIsInclusive)
	{
		return shorts.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveShortValidator isMultipleOf(short factor)
	{
		return shorts.isMultipleOf(factor);
	}

	@Override
	public PrimitiveShortValidator isMultipleOf(short factor, String name)
	{
		return shorts.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveShortValidator isNotMultipleOf(short factor)
	{
		return shorts.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveShortValidator isNotMultipleOf(short factor, String name)
	{
		return shorts.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveShortValidator isLessThan(Short maximumExclusive)
	{
		return shorts.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveShortValidator isLessThan(Short maximumExclusive, String name)
	{
		return shorts.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveShortValidator isLessThanOrEqualTo(Short maximumInclusive)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveShortValidator isLessThanOrEqualTo(Short maximumInclusive, String name)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(Short minimumInclusive)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(Short minimumInclusive, String name)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveShortValidator isGreaterThan(Short minimumExclusive)
	{
		return shorts.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveShortValidator isGreaterThan(Short minimumExclusive, String name)
	{
		return shorts.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveShortValidator isBetween(Short minimumInclusive, Short maximumExclusive)
	{
		return shorts.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveShortValidator isBetween(Short minimum, boolean minimumIsInclusive, Short maximum,
		boolean maximumIsInclusive)
	{
		return shorts.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}