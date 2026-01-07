/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.Arrays;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveIntegerArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveIntegerArrayValidator, int[], Integer>
	implements PrimitiveIntegerArrayValidator
{
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
	public PrimitiveIntegerArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<int[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
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