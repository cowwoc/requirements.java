package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class PrimitiveCharacterValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	private final Characters<PrimitiveCharacterValidator> characters = new Characters<>(this);

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
	public PrimitiveCharacterValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Character> value, Map<String, Object> context, List<ValidationFailure> failures)
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
		return value.orDefault(defaultValue);
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
	public PrimitiveCharacterValidator isBetween(char minimum, boolean minimumInclusive, char maximum,
		boolean maximumInclusive)
	{
		return characters.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
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
	public PrimitiveCharacterValidator isBetween(Character minimum, boolean minimumInclusive, Character maximum,
		boolean maximumInclusive)
	{
		return characters.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}