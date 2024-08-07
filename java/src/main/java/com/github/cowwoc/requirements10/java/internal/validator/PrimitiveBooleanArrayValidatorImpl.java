package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Arrays;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveBooleanArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveBooleanArrayValidator, boolean[], Boolean>
	implements PrimitiveBooleanArrayValidator
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
	public PrimitiveBooleanArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<boolean[]> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(boolean[] array)
	{
		return array.length;
	}

	@Override
	protected List<Boolean> asList(boolean[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(boolean[] array, Boolean element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Boolean> getDuplicates(boolean[] value)
	{
		Set<Boolean> unique = new HashSet<>(value.length);
		Set<Boolean> duplicates = new HashSet<>(value.length);
		for (boolean element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveBooleanArrayValidator contains(boolean expected)
	{
		return contains((Boolean) expected);
	}

	@Override
	public PrimitiveBooleanArrayValidator contains(boolean expected, String name)
	{
		return contains((Boolean) expected, name);
	}

	@Override
	public PrimitiveBooleanArrayValidator doesNotContain(boolean unwanted)
	{
		return doesNotContain((Boolean) unwanted);
	}

	@Override
	public PrimitiveBooleanArrayValidator doesNotContain(boolean unwanted, String name)
	{
		return doesNotContain((Boolean) unwanted, name);
	}
}