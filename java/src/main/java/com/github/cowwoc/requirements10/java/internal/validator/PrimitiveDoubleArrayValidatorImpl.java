package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Arrays;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveDoubleArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveDoubleArrayValidator, double[], Double>
	implements PrimitiveDoubleArrayValidator
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
	public PrimitiveDoubleArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<double[]> value, Map<String, Object> context, List<ValidationFailure> failures)
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