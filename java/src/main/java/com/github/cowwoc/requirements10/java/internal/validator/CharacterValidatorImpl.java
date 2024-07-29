package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.validator.CharacterValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class CharacterValidatorImpl extends AbstractObjectValidator<CharacterValidator, Character>
	implements CharacterValidator
{
	private final Characters<CharacterValidator> characters = new Characters<>(this);

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
	public CharacterValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Character> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public CharacterValidator isLessThan(char maximumExclusive)
	{
		return characters.isLessThan(maximumExclusive);
	}

	@Override
	public CharacterValidator isLessThan(char maximumExclusive, String name)
	{
		return characters.isLessThan(maximumExclusive, name);
	}

	@Override
	public CharacterValidator isLessThanOrEqualTo(char maximumInclusive)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public CharacterValidator isLessThanOrEqualTo(char maximumInclusive, String name)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public CharacterValidator isGreaterThanOrEqualTo(char minimumInclusive)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public CharacterValidator isGreaterThanOrEqualTo(char minimumInclusive, String name)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public CharacterValidator isGreaterThan(char minimumExclusive)
	{
		return characters.isGreaterThan(minimumExclusive);
	}

	@Override
	public CharacterValidator isGreaterThan(char minimumExclusive, String name)
	{
		return characters.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public CharacterValidator isBetween(char minimumInclusive, char maximumExclusive)
	{
		return characters.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public CharacterValidator isBetween(char minimum, boolean minimumInclusive, char maximum,
		boolean maximumInclusive)
	{
		return characters.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public CharacterValidator isLessThan(Character maximumExclusive)
	{
		return characters.isLessThan(maximumExclusive);
	}

	@Override
	public CharacterValidator isLessThan(Character maximumExclusive, String name)
	{
		return characters.isLessThan(maximumExclusive, name);
	}

	@Override
	public CharacterValidator isLessThanOrEqualTo(Character maximumInclusive)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public CharacterValidator isLessThanOrEqualTo(Character maximumInclusive, String name)
	{
		return characters.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public CharacterValidator isGreaterThanOrEqualTo(Character minimumInclusive)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public CharacterValidator isGreaterThanOrEqualTo(Character minimumInclusive, String name)
	{
		return characters.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public CharacterValidator isGreaterThan(Character minimumExclusive)
	{
		return characters.isGreaterThan(minimumExclusive);
	}

	@Override
	public CharacterValidator isGreaterThan(Character minimumExclusive, String name)
	{
		return characters.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public CharacterValidator isBetween(Character minimumInclusive, Character maximumExclusive)
	{
		return characters.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public CharacterValidator isBetween(Character minimum, boolean minimumInclusive, Character maximum,
		boolean maximumInclusive)
	{
		return characters.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}