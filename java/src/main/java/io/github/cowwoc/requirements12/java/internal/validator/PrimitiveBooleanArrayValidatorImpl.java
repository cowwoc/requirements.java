/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.Arrays;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.validator.PrimitiveBooleanArrayValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveBooleanArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveBooleanArrayValidator, boolean[], Boolean>
	implements PrimitiveBooleanArrayValidator
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
	public PrimitiveBooleanArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<boolean[]> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
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