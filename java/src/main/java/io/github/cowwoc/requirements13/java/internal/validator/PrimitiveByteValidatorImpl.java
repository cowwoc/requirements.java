/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.PrimitiveByteValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PrimitiveByteValidatorImpl extends AbstractPrimitiveValidator<PrimitiveByteValidator, Byte>
	implements PrimitiveByteValidator
{
	private final Bytes<PrimitiveByteValidator> bytes = new Bytes<>(this);

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
	public PrimitiveByteValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Byte> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public byte getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public byte getValueOrDefault(byte defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveByteValidator isEqualTo(byte expected)
	{
		return bytes.isEqualTo(expected);
	}

	@Override
	public PrimitiveByteValidator isEqualTo(byte expected, String name)
	{
		return bytes.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveByteValidator isNotEqualTo(byte unwanted)
	{
		return bytes.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveByteValidator isNotEqualTo(byte unwanted, String name)
	{
		return bytes.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveByteValidator isNegative()
	{
		return bytes.isNegative();
	}

	@Override
	public PrimitiveByteValidator isNotNegative()
	{
		return bytes.isNotNegative();
	}

	@Override
	public PrimitiveByteValidator isZero()
	{
		return bytes.isZero();
	}

	@Override
	public PrimitiveByteValidator isNotZero()
	{
		return bytes.isNotZero();
	}

	@Override
	public PrimitiveByteValidator isPositive()
	{
		return bytes.isPositive();
	}

	@Override
	public PrimitiveByteValidator isNotPositive()
	{
		return bytes.isNotPositive();
	}

	@Override
	public PrimitiveByteValidator isLessThan(byte maximumExclusive)
	{
		return bytes.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveByteValidator isLessThan(byte maximumExclusive, String name)
	{
		return bytes.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveByteValidator isLessThanOrEqualTo(byte maximumInclusive)
	{
		return bytes.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveByteValidator isLessThanOrEqualTo(byte maximumInclusive, String name)
	{
		return bytes.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveByteValidator isGreaterThanOrEqualTo(byte minimumInclusive)
	{
		return bytes.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveByteValidator isGreaterThanOrEqualTo(byte minimumInclusive, String name)
	{
		return bytes.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(byte minimumExclusive)
	{
		return bytes.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(byte minimumExclusive, String name)
	{
		return bytes.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveByteValidator isBetween(byte minimumInclusive, byte maximumExclusive)
	{
		return bytes.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveByteValidator isBetween(byte minimum, boolean minimumIsInclusive, byte maximum,
		boolean maximumIsInclusive)
	{
		return bytes.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveByteValidator isMultipleOf(byte factor)
	{
		return bytes.isMultipleOf(factor);
	}

	@Override
	public PrimitiveByteValidator isMultipleOf(byte factor, String name)
	{
		return bytes.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveByteValidator isNotMultipleOf(byte factor)
	{
		return bytes.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveByteValidator isNotMultipleOf(byte factor, String name)
	{
		return bytes.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveByteValidator isLessThan(Byte maximumExclusive)
	{
		return bytes.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveByteValidator isLessThan(Byte maximumExclusive, String name)
	{
		return bytes.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveByteValidator isLessThanOrEqualTo(Byte maximumInclusive)
	{
		return bytes.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveByteValidator isLessThanOrEqualTo(Byte maximumInclusive, String name)
	{
		return bytes.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveByteValidator isGreaterThanOrEqualTo(Byte minimumInclusive)
	{
		return bytes.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveByteValidator isGreaterThanOrEqualTo(Byte minimumInclusive, String name)
	{
		return bytes.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(Byte minimumExclusive)
	{
		return bytes.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(Byte minimumExclusive, String name)
	{
		return bytes.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveByteValidator isBetween(Byte minimumInclusive, Byte maximumExclusive)
	{
		return bytes.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveByteValidator isBetween(Byte minimum, boolean minimumIsInclusive, Byte maximum,
		boolean maximumIsInclusive)
	{
		return bytes.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}