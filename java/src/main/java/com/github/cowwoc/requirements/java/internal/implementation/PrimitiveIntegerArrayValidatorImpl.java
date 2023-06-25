package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerArrayValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveIntegerArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveIntegerArrayValidator, Integer, int[]>
	implements PrimitiveIntegerArrayValidator
{
	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PrimitiveIntegerArrayValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator,
		String name, int[] value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PrimitiveIntegerArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, int[] value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private PrimitiveIntegerArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, int[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(int[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Integer> getDuplicates(int[] value)
	{
		Set<Integer> unique = new HashSet<>(value.length);
		Set<Integer> duplicates = new HashSet<>(value.length);
		for (int element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveIntegerArrayValidator isSorted()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isSorted(scope, this, this.name, null, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			if (Arrays.isSorted(value))
			{
				addIllegalArgumentException(
					CollectionMessages.isSorted(scope, this, this.name, value, Arrays.sorted(value)).
						toString());
			}
		}
		return self();
	}

	@Override
	public PrimitiveIntegerArrayValidator isSorted(Comparator<Integer> comparator)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isSorted(scope, this, this.name, null, comparator).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			if (isSorted(value, comparator))
			{
				addIllegalArgumentException(
					CollectionMessages.isSorted(scope, this, this.name, value, sorted(value, comparator)).
						toString());
			}
		}
		return self();
	}

	@Override
	protected boolean isSorted(int[] array, Comparator<Integer> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Integer> sorted(int[] array, Comparator<Integer> comparator)
	{
		List<Integer> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl contains(int expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl contains(Integer expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((int) expected, null);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl contains(int expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl contains(Integer expected, String name)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((int) expected, name);
	}

	private PrimitiveIntegerArrayValidatorImpl containsImpl(int expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.contains(scope, this, this.name, null, null, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Arrays.contains(value, expected))
		{
			addIllegalArgumentException(
				CollectionMessages.contains(scope, this, this.name, value, name, expected).toString());
		}
		return this;
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl doesNotContain(int unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl doesNotContain(Integer unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((int) unwanted, null);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl doesNotContain(int unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveIntegerArrayValidatorImpl doesNotContain(Integer unwanted, String name)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((int) unwanted, name);
	}

	private PrimitiveIntegerArrayValidatorImpl doesNotContainImpl(int unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContain(scope, this, this.name, null, null, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (Arrays.contains(value, unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContain(scope, this, this.name, value, name, unwanted).toString());
		}
		return this;
	}
}