package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveShortArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveShortArrayValidator, Short, short[]>
	implements PrimitiveShortArrayValidator
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
	public PrimitiveShortArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		short[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(short[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Short> getDuplicates(short[] value)
	{
		Set<Short> unique = new HashSet<>(value.length);
		Set<Short> duplicates = new HashSet<>(value.length);
		for (short element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveShortArrayValidator isSorted()
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
		return this;
	}

	@Override
	public PrimitiveShortArrayValidator isSorted(Comparator<Short> comparator)
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
	protected boolean isSorted(short[] array, Comparator<Short> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Short> sorted(short[] array, Comparator<Short> comparator)
	{
		List<Short> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public PrimitiveShortArrayValidatorImpl contains(short expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl contains(Short expected)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((short) expected, null);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl contains(short expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl contains(Short expected, String name)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((short) expected, name);
	}

	private PrimitiveShortArrayValidatorImpl containsImpl(short expected, String name)
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
	public PrimitiveShortArrayValidatorImpl doesNotContain(short unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl doesNotContain(Short unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((short) unwanted, null);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl doesNotContain(short unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveShortArrayValidatorImpl doesNotContain(Short unwanted, String name)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((short) unwanted, name);
	}

	private PrimitiveShortArrayValidatorImpl doesNotContainImpl(short unwanted, String name)
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

	@Override
	public ObjectArrayValidator<Short, Short[]> asBoxed()
	{
		if (hasFailed())
			return new ObjectArrayValidatorImpl<>(scope, configuration, name, null, context, failures);
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new ObjectArrayValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new ObjectArrayValidatorImpl<>(scope, configuration, name, Arrays.asBoxed(value), context,
			failures);
	}
}