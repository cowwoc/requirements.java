package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveUnsignedIntegerValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveUnsignedIntegerValidator, Integer>
	implements PrimitiveUnsignedIntegerValidator
{
	private final Integers<PrimitiveUnsignedIntegerValidator> integers = new Integers<>(this);

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
	public PrimitiveUnsignedIntegerValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name,
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
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected)
	{
		return integers.isEqualTo(expected);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected, String name)
	{
		return integers.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted)
	{
		return integers.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		return integers.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isZero()
	{
		return integers.isZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotZero()
	{
		return integers.isNotZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isPositive()
	{
		return integers.isPositive();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotPositive()
	{
		return integers.isNotPositive();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimum, boolean minimumInclusive, int maximum,
		boolean maximumInclusive)
	{
		return integers.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor)
	{
		return integers.isMultipleOf(factor);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor, String name)
	{
		return integers.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor)
	{
		return integers.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor, String name)
	{
		return integers.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimum, boolean minimumInclusive,
		Integer maximum, boolean maximumInclusive)
	{
		return integers.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}