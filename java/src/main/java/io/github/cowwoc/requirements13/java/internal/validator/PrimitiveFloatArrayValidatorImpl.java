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
import io.github.cowwoc.requirements13.java.validator.PrimitiveFloatArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveFloatArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveFloatArrayValidator, float[], Float>
	implements PrimitiveFloatArrayValidator
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
	public PrimitiveFloatArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<float[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(float[] array)
	{
		return array.length;
	}

	@Override
	protected List<Float> asList(float[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(float[] array, Float element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Float> getDuplicates(float[] value)
	{
		Set<Float> unique = new HashSet<>(value.length);
		Set<Float> duplicates = new HashSet<>(value.length);
		for (float element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveFloatArrayValidator contains(float expected)
	{
		return contains((Float) expected);
	}

	@Override
	public PrimitiveFloatArrayValidator contains(float expected, String name)
	{
		return contains((Float) expected, name);
	}

	@Override
	public PrimitiveFloatArrayValidator doesNotContain(float unwanted)
	{
		return doesNotContain((Float) unwanted);
	}

	@Override
	public PrimitiveFloatArrayValidator doesNotContain(float unwanted, String name)
	{
		return doesNotContain((Float) unwanted, name);
	}

	@Override
	public PrimitiveFloatArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}