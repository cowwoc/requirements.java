package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.PrimitiveIntegerValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveIntegerValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveIntegerValidator, Integer>
	implements PrimitiveIntegerValidator
{
	private final Integers<PrimitiveIntegerValidator> integers = new Integers<>(this);

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
	public PrimitiveIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Integer> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public int getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public int getValueOrDefault(int defaultValue)
	{
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveIntegerValidator isEqualTo(int expected)
	{
		return integers.isEqualTo(expected);
	}

	@Override
	public PrimitiveIntegerValidator isEqualTo(int expected, String name)
	{
		return integers.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveIntegerValidator isNotEqualTo(int unwanted)
	{
		return integers.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		return integers.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveIntegerValidator isNegative()
	{
		return integers.isNegative();
	}

	@Override
	public PrimitiveIntegerValidator isNotNegative()
	{
		return integers.isNotNegative();
	}

	@Override
	public PrimitiveIntegerValidator isZero()
	{
		return integers.isZero();
	}

	@Override
	public PrimitiveIntegerValidator isNotZero()
	{
		return integers.isNotZero();
	}

	@Override
	public PrimitiveIntegerValidator isPositive()
	{
		return integers.isPositive();
	}

	@Override
	public PrimitiveIntegerValidator isNotPositive()
	{
		return integers.isNotPositive();
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(int maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(int minimum, boolean minimumInclusive, int maximum,
		boolean maximumInclusive)
	{
		return integers.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isMultipleOf(int factor)
	{
		return integers.isMultipleOf(factor);
	}

	@Override
	public PrimitiveIntegerValidator isMultipleOf(int factor, String name)
	{
		return integers.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveIntegerValidator isNotMultipleOf(int factor)
	{
		return integers.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveIntegerValidator isNotMultipleOf(int factor, String name)
	{
		return integers.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(Integer minimum, boolean minimumInclusive, Integer maximum,
		boolean maximumInclusive)
	{
		return integers.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}