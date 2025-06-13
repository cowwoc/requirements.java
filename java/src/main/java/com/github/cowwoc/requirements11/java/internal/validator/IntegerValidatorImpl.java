package com.github.cowwoc.requirements11.java.internal.validator;

import com.github.cowwoc.requirements11.java.ValidationFailure;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements11.java.validator.IntegerValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class IntegerValidatorImpl extends AbstractObjectValidator<IntegerValidator, Integer>
	implements IntegerValidator
{
	private final Integers<IntegerValidator> integers = new Integers<>(this);

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public IntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Integer> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public IntegerValidator isNegative()
	{
		return integers.isNegative();
	}

	@Override
	public IntegerValidator isNotNegative()
	{
		return integers.isNotNegative();
	}

	@Override
	public IntegerValidator isZero()
	{
		return integers.isZero();
	}

	@Override
	public IntegerValidator isNotZero()
	{
		return integers.isNotZero();
	}

	@Override
	public IntegerValidator isPositive()
	{
		return integers.isPositive();
	}

	@Override
	public IntegerValidator isNotPositive()
	{
		return integers.isNotPositive();
	}

	@Override
	public IntegerValidator isLessThan(int maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public IntegerValidator isLessThan(int maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public IntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public IntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public IntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public IntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public IntegerValidator isGreaterThan(int minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public IntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public IntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public IntegerValidator isBetween(int minimum, boolean minimumIsInclusive, int maximum,
		boolean maximumIsInclusive)
	{
		return integers.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public IntegerValidator isMultipleOf(int factor)
	{
		return integers.isMultipleOf(factor);
	}

	@Override
	public IntegerValidator isMultipleOf(int factor, String name)
	{
		return integers.isMultipleOf(factor, name);
	}

	@Override
	public IntegerValidator isNotMultipleOf(int factor)
	{
		return integers.isNotMultipleOf(factor);
	}

	@Override
	public IntegerValidator isNotMultipleOf(int factor, String name)
	{
		return integers.isNotMultipleOf(factor, name);
	}

	@Override
	public IntegerValidator isLessThan(Integer maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public IntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public IntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public IntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public IntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public IntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public IntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public IntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public IntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public IntegerValidator isBetween(Integer minimum, boolean minimumIsInclusive, Integer maximum,
		boolean maximumIsInclusive)
	{
		return integers.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}