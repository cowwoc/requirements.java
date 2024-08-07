package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Arrays;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveLongArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveLongArrayValidator, long[], Long>
	implements PrimitiveLongArrayValidator
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
	public PrimitiveLongArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<long[]> value, Map<String, Object> context, List<ValidationFailure> failures)
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