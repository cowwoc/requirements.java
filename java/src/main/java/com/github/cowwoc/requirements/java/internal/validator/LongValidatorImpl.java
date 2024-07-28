package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.LongValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class LongValidatorImpl extends AbstractObjectValidator<LongValidator, Long>
	implements LongValidator
{
	private final Longs<LongValidator> longs = new Longs<>(this);

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
	public LongValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Long> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public LongValidator isNegative()
	{
		return longs.isNegative();
	}

	@Override
	public LongValidator isNotNegative()
	{
		return longs.isNotNegative();
	}

	@Override
	public LongValidator isZero()
	{
		return longs.isZero();
	}

	@Override
	public LongValidator isNotZero()
	{
		return longs.isNotZero();
	}

	@Override
	public LongValidator isPositive()
	{
		return longs.isPositive();
	}

	@Override
	public LongValidator isNotPositive()
	{
		return longs.isNotPositive();
	}

	@Override
	public LongValidator isLessThan(long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public LongValidator isLessThan(long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public LongValidator isLessThanOrEqualTo(long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public LongValidator isLessThanOrEqualTo(long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public LongValidator isGreaterThanOrEqualTo(long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public LongValidator isGreaterThanOrEqualTo(long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public LongValidator isGreaterThan(long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public LongValidator isGreaterThan(long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public LongValidator isBetween(long minimumInclusive, long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public LongValidator isBetween(long minimum, boolean minimumInclusive, long maximum,
		boolean maximumInclusive)
	{
		return longs.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public LongValidator isMultipleOf(long factor)
	{
		return longs.isMultipleOf(factor);
	}

	@Override
	public LongValidator isMultipleOf(long factor, String name)
	{
		return longs.isMultipleOf(factor, name);
	}

	@Override
	public LongValidator isNotMultipleOf(long factor)
	{
		return longs.isNotMultipleOf(factor);
	}

	@Override
	public LongValidator isNotMultipleOf(long factor, String name)
	{
		return longs.isNotMultipleOf(factor, name);
	}

	@Override
	public LongValidator isLessThan(Long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public LongValidator isLessThan(Long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public LongValidator isLessThanOrEqualTo(Long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public LongValidator isLessThanOrEqualTo(Long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public LongValidator isGreaterThanOrEqualTo(Long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public LongValidator isGreaterThanOrEqualTo(Long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public LongValidator isGreaterThan(Long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public LongValidator isGreaterThan(Long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public LongValidator isBetween(Long minimumInclusive, Long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public LongValidator isBetween(Long minimum, boolean minimumInclusive, Long maximum,
		boolean maximumInclusive)
	{
		return longs.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}