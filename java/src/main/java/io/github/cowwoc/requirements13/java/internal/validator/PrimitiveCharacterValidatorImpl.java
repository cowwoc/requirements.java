/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.PrimitiveCharacterValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PrimitiveCharacterValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	private final Characters<PrimitiveCharacterValidator> characters = new Characters<>(this);

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
	public PrimitiveCharacterValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Character> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public char getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public char getValueOrDefault(char defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveCharacterValidator isEqualTo(char expected)
	{
		return characters.isEqualTo(expected);
	}

	@Override
	public PrimitiveCharacterValidator isEqualTo(char expected, String name)
	{
		return characters.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveCharacterValidator isNotEqualTo(char unwanted)
	{
		return characters.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveCharacterValidator isNotEqualTo(char unwanted, String name)
	{
		return characters.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(char maximumExclusive)
	{
		return characters.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(char maximumExclusive, String name)
	{
		return characters.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive, String name)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive, String name)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThan(char minimumExclusive)
	{
		return characters.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThan(char minimumExclusive, String name)
	{
		return characters.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isBetween(char minimumInclusive, char maximumExclusive)
	{
		return characters.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isBetween(char minimum, boolean minimumIsInclusive, char maximum,
		boolean maximumIsInclusive)
	{
		return characters.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(Character maximumExclusive)
	{
		return characters.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(Character maximumExclusive, String name)
	{
		return characters.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(Character maximumInclusive)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(Character maximumInclusive, String name)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(Character minimumInclusive)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(Character minimumInclusive, String name)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThan(Character minimumExclusive)
	{
		return characters.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThan(Character minimumExclusive, String name)
	{
		return characters.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveCharacterValidator isBetween(Character minimumInclusive, Character maximumExclusive)
	{
		return characters.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveCharacterValidator isBetween(Character minimum, boolean minimumIsInclusive,
		Character maximum, boolean maximumIsInclusive)
	{
		return characters.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}