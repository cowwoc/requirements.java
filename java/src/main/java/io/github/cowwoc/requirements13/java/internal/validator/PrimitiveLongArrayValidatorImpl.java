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
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveLongArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveLongArrayValidator, long[], Long>
	implements PrimitiveLongArrayValidator
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
	public PrimitiveLongArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<long[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(long[] array)
	{
		return array.length;
	}

	@Override
	protected List<Long> asList(long[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(long[] array, Long element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Long> getDuplicates(long[] value)
	{
		Set<Long> unique = new HashSet<>(value.length);
		Set<Long> duplicates = new HashSet<>(value.length);
		for (long element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveLongArrayValidator contains(long expected)
	{
		return contains((Long) expected);
	}

	@Override
	public PrimitiveLongArrayValidator contains(long expected, String name)
	{
		return contains((Long) expected, name);
	}

	@Override
	public PrimitiveLongArrayValidator doesNotContain(long unwanted)
	{
		return doesNotContain((Long) unwanted);
	}

	@Override
	public PrimitiveLongArrayValidator doesNotContain(long unwanted, String name)
	{
		return doesNotContain((Long) unwanted, name);
	}

	@Override
	public PrimitiveLongArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}