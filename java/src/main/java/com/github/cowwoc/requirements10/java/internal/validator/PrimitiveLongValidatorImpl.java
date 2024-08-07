package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class PrimitiveLongValidatorImpl extends AbstractPrimitiveValidator<PrimitiveLongValidator, Long>
	implements PrimitiveLongValidator
{
	private final Longs<PrimitiveLongValidator> longs = new Longs<>(this);

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
	public PrimitiveLongValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Long> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public long getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public long getValueOrDefault(long defaultValue)
	{
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveLongValidator isEqualTo(long expected)
	{
		return longs.isEqualTo(expected);
	}

	@Override
	public PrimitiveLongValidator isEqualTo(long expected, String name)
	{
		return longs.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveLongValidator isNotEqualTo(long unwanted)
	{
		return longs.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveLongValidator isNotEqualTo(long unwanted, String name)
	{
		return longs.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveLongValidator isNegative()
	{
		return longs.isNegative();
	}

	@Override
	public PrimitiveLongValidator isNotNegative()
	{
		return longs.isNotNegative();
	}

	@Override
	public PrimitiveLongValidator isZero()
	{
		return longs.isZero();
	}

	@Override
	public PrimitiveLongValidator isNotZero()
	{
		return longs.isNotZero();
	}

	@Override
	public PrimitiveLongValidator isPositive()
	{
		return longs.isPositive();
	}

	@Override
	public PrimitiveLongValidator isNotPositive()
	{
		return longs.isNotPositive();
	}

	@Override
	public PrimitiveLongValidator isLessThan(long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThan(long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isBetween(long minimumInclusive, long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isBetween(long minimum, boolean minimumInclusive, long maximum,
		boolean maximumInclusive)
	{
		return longs.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public PrimitiveLongValidator isMultipleOf(long factor)
	{
		return longs.isMultipleOf(factor);
	}

	@Override
	public PrimitiveLongValidator isMultipleOf(long factor, String name)
	{
		return longs.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveLongValidator isNotMultipleOf(long factor)
	{
		return longs.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveLongValidator isNotMultipleOf(long factor, String name)
	{
		return longs.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveLongValidator isLessThan(Long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThan(Long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(Long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(Long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(Long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(Long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(Long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(Long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isBetween(Long minimumInclusive, Long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isBetween(Long minimum, boolean minimumInclusive, Long maximum,
		boolean maximumInclusive)
	{
		return longs.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}