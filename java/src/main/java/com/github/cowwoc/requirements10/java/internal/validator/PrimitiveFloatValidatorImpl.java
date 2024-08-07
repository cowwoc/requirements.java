package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class PrimitiveFloatValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveFloatValidator, Float>
	implements PrimitiveFloatValidator
{
	private final Floats<PrimitiveFloatValidator> floats = new Floats<>(this);

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
	public PrimitiveFloatValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Float> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public float getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public float getValueOrDefault(float defaultValue)
	{
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveFloatValidator isEqualTo(float expected)
	{
		return floats.isEqualTo(expected);
	}

	@Override
	public PrimitiveFloatValidator isEqualTo(float expected, String name)
	{
		return floats.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveFloatValidator isNotEqualTo(float unwanted)
	{
		return floats.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveFloatValidator isNotEqualTo(float unwanted, String name)
	{
		return floats.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveFloatValidator isNegative()
	{
		return floats.isNegative();
	}

	@Override
	public PrimitiveFloatValidator isNotNegative()
	{
		return floats.isNotNegative();
	}

	@Override
	public PrimitiveFloatValidator isZero()
	{
		return floats.isZero();
	}

	@Override
	public PrimitiveFloatValidator isNotZero()
	{
		return floats.isNotZero();
	}

	@Override
	public PrimitiveFloatValidator isPositive()
	{
		return floats.isPositive();
	}

	@Override
	public PrimitiveFloatValidator isNotPositive()
	{
		return floats.isNotPositive();
	}

	@Override
	public PrimitiveFloatValidator isLessThan(float maximumExclusive)
	{
		return floats.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isLessThan(float maximumExclusive, String name)
	{
		return floats.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(float maximumInclusive)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(float maximumInclusive, String name)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(float minimumInclusive)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(float minimumInclusive, String name)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(float minimumExclusive)
	{
		return floats.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(float minimumExclusive, String name)
	{
		return floats.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isBetween(float minimumInclusive, float maximumExclusive)
	{
		return floats.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isBetween(float minimum, boolean minimumInclusive, float maximum,
		boolean maximumInclusive)
	{
		return floats.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isMultipleOf(float factor)
	{
		return floats.isMultipleOf(factor);
	}

	@Override
	public PrimitiveFloatValidator isMultipleOf(float factor, String name)
	{
		return floats.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveFloatValidator isNotMultipleOf(float factor)
	{
		return floats.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveFloatValidator isNotMultipleOf(float factor, String name)
	{
		return floats.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveFloatValidator isLessThan(Float maximumExclusive)
	{
		return floats.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isLessThan(Float maximumExclusive, String name)
	{
		return floats.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(Float maximumInclusive)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(Float maximumInclusive, String name)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(Float minimumInclusive)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(Float minimumInclusive, String name)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(Float minimumExclusive)
	{
		return floats.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(Float minimumExclusive, String name)
	{
		return floats.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveFloatValidator isBetween(Float minimumInclusive, Float maximumExclusive)
	{
		return floats.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveFloatValidator isBetween(Float minimum, boolean minimumInclusive, Float maximum,
		boolean maximumInclusive)
	{
		return floats.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public PrimitiveFloatValidator isNumber()
	{
		return floats.isNumber();
	}

	@Override
	public PrimitiveFloatValidator isNotNumber()
	{
		return floats.isNotNumber();
	}

	@Override
	public PrimitiveFloatValidator isFinite()
	{
		return floats.isFinite();
	}

	@Override
	public PrimitiveFloatValidator isInfinite()
	{
		return floats.isInfinite();
	}

	@Override
	public PrimitiveFloatValidator isWholeNumber()
	{
		return floats.isWholeNumber();
	}

	@Override
	public PrimitiveFloatValidator isNotWholeNumber()
	{
		return floats.isNotWholeNumber();
	}
}