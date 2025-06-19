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
import io.github.cowwoc.requirements12.java.validator.PrimitiveDoubleArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveDoubleArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveDoubleArrayValidator, double[], Double>
	implements PrimitiveDoubleArrayValidator
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
	public PrimitiveDoubleArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<double[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(double[] array)
	{
		return array.length;
	}

	@Override
	protected List<Double> asList(double[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(double[] array, Double element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Double> getDuplicates(double[] value)
	{
		Set<Double> unique = new HashSet<>(value.length);
		Set<Double> duplicates = new HashSet<>(value.length);
		for (double element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(double expected)
	{
		return contains((Double) expected);
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(double expected, String name)
	{
		return contains((Double) expected, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator doesNotContain(double unwanted)
	{
		return doesNotContain((Double) unwanted);
	}

	@Override
	public PrimitiveDoubleArrayValidator doesNotContain(double unwanted, String name)
	{
		return doesNotContain((Double) unwanted, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}