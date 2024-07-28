package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveIntegerArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveIntegerArrayValidator, int[], Integer>
	implements PrimitiveIntegerArrayValidator
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
	public PrimitiveIntegerArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<int[]> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(int[] array)
	{
		return array.length;
	}

	@Override
	protected List<Integer> asList(int[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(int[] array, Integer element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Integer> getDuplicates(int[] value)
	{
		Set<Integer> unique = new HashSet<>(value.length);
		Set<Integer> duplicates = new HashSet<>(value.length);
		for (int element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveIntegerArrayValidator contains(int expected)
	{
		return contains((Integer) expected);
	}

	@Override
	public PrimitiveIntegerArrayValidator contains(int expected, String name)
	{
		return contains((Integer) expected, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator doesNotContain(int unwanted)
	{
		return doesNotContain((Integer) unwanted);
	}

	@Override
	public PrimitiveIntegerArrayValidator doesNotContain(int unwanted, String name)
	{
		return doesNotContain((Integer) unwanted, name);
	}

	@Override
	public PrimitiveIntegerArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}