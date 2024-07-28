package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveCharacterArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveCharacterArrayValidator, char[], Character>
	implements PrimitiveCharacterArrayValidator
{
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
	public PrimitiveCharacterArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, MaybeUndefined<char[]> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(char[] array)
	{
		return array.length;
	}

	@Override
	protected List<Character> asList(char[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(char[] array, Character element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Character> getDuplicates(char[] value)
	{
		Set<Character> unique = new HashSet<>(value.length);
		Set<Character> duplicates = new HashSet<>(value.length);
		for (char element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(char expected)
	{
		return contains((Character) expected);
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(char expected, String name)
	{
		return contains((Character) expected, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator doesNotContain(char unwanted)
	{
		return doesNotContain((Character) unwanted);
	}

	@Override
	public PrimitiveCharacterArrayValidator doesNotContain(char unwanted, String name)
	{
		return doesNotContain((Character) unwanted, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}