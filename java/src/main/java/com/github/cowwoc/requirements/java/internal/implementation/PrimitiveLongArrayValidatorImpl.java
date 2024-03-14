package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.PrimitiveLongArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveLongArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveLongArrayValidator, Long, long[]>
	implements PrimitiveLongArrayValidator
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
	public PrimitiveLongArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		long[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(long[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Long> getDuplicates(long[] value)
	{
		Set<Long> unique = new HashSet<>(value.length);
		Set<Long> duplicates = new HashSet<>(value.length);
		for (long element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveLongArrayValidator isSorted()
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
	public PrimitiveLongArrayValidator isSorted(Comparator<Long> comparator)
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
	protected boolean isSorted(long[] array, Comparator<Long> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Long> sorted(long[] array, Comparator<Long> comparator)
	{
		List<Long> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public PrimitiveLongArrayValidatorImpl contains(long expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveLongArrayValidator contains(Long expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((long) expected);
	}

	@Override
	public PrimitiveLongArrayValidatorImpl contains(long expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveLongArrayValidator contains(Long expected, String name)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((long) expected, name);
	}

	private PrimitiveLongArrayValidatorImpl containsImpl(long expected, String name)
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
	public PrimitiveLongArrayValidatorImpl doesNotContain(long unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveLongArrayValidatorImpl doesNotContain(Long unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((long) unwanted, null);
	}

	@Override
	public PrimitiveLongArrayValidatorImpl doesNotContain(long unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveLongArrayValidatorImpl doesNotContain(Long unwanted, String name)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((long) unwanted, name);
	}

	private PrimitiveLongArrayValidatorImpl doesNotContainImpl(long unwanted, String name)
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