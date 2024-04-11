package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveBooleanArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveBooleanArrayValidator, Boolean, boolean[]>
	implements PrimitiveBooleanArrayValidator
{
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
	public PrimitiveBooleanArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		boolean[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(boolean[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Boolean> getDuplicates(boolean[] value)
	{
		Set<Boolean> unique = new HashSet<>(value.length);
		Set<Boolean> duplicates = new HashSet<>(value.length);
		for (boolean element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveBooleanArrayValidator isSorted(Comparator<Boolean> comparator)
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
		return this;
	}

	@Override
	protected boolean isSorted(boolean[] array, Comparator<Boolean> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Boolean> sorted(boolean[] array, Comparator<Boolean> comparator)
	{
		List<Boolean> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl contains(boolean expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl contains(Boolean expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((boolean) expected, null);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl contains(boolean expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl contains(Boolean expected, String name)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((boolean) expected, name);
	}

	private PrimitiveBooleanArrayValidatorImpl containsImpl(boolean expected, String name)
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
	public PrimitiveBooleanArrayValidatorImpl doesNotContain(boolean unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl doesNotContain(Boolean unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((boolean) unwanted, null);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl doesNotContain(boolean unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveBooleanArrayValidatorImpl doesNotContain(Boolean unwanted, String name)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((boolean) unwanted, name);
	}

	private PrimitiveBooleanArrayValidatorImpl doesNotContainImpl(boolean unwanted, String name)
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