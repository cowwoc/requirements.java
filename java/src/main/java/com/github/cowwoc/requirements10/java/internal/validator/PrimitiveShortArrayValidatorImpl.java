package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Arrays;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PrimitiveShortArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveShortArrayValidator, short[], Short>
	implements PrimitiveShortArrayValidator
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
	public PrimitiveShortArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<short[]> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(short[] array)
	{
		return array.length;
	}

	@Override
	protected List<Short> asList(short[] array)
	{
		return Arrays.asList(array);
	}

	@Override
	protected boolean contains(short[] array, Short element)
	{
		return Arrays.contains(array, element);
	}

	@Override
	protected Set<Short> getDuplicates(short[] value)
	{
		Set<Short> unique = new HashSet<>(value.length);
		Set<Short> duplicates = new HashSet<>(value.length);
		for (short element : value)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		return duplicates;
	}

	@Override
	public PrimitiveShortArrayValidator contains(short expected)
	{
		return contains((Short) expected);
	}

	@Override
	public PrimitiveShortArrayValidator contains(short expected, String name)
	{
		return contains((Short) expected, name);
	}

	@Override
	public PrimitiveShortArrayValidator doesNotContain(short unwanted)
	{
		return doesNotContain((Short) unwanted);
	}

	@Override
	public PrimitiveShortArrayValidator doesNotContain(short unwanted, String name)
	{
		return doesNotContain((Short) unwanted, name);
	}

	@Override
	public PrimitiveShortArrayValidator isSorted()
	{
		return isSorted(Comparator.naturalOrder());
	}
}