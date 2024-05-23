package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveCharacterArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveCharacterArrayValidator, Character, char[]>
	implements PrimitiveCharacterArrayValidator
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
	public PrimitiveCharacterArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, char[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(char[] array)
	{
		return array.length;
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(char expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(Character expected)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((char) expected, null);
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(char expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator contains(Character expected, String name)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((char) expected, name);
	}

	private PrimitiveCharacterArrayValidator containsImpl(char expected, String name)
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
	public PrimitiveCharacterArrayValidator doesNotContain(char unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveCharacterArrayValidator doesNotContain(Character unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((char) unwanted, null);
	}

	@Override
	public PrimitiveCharacterArrayValidator doesNotContain(char unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveCharacterArrayValidator doesNotContain(Character unwanted, String name)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((char) unwanted, name);
	}

	private PrimitiveCharacterArrayValidator doesNotContainImpl(char unwanted, String name)
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
	protected Set<Character> getDuplicates(char[] value)
	{
		Set<Character> unique = new HashSet<>(value.length);
		Set<Character> duplicates = new HashSet<>(value.length);
		for (char element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveCharacterArrayValidator isSorted()
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
	public PrimitiveCharacterArrayValidator isSorted(Comparator<Character> comparator)
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
	protected boolean isSorted(char[] array, Comparator<Character> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Character> sorted(char[] array, Comparator<Character> comparator)
	{
		List<Character> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public ObjectArrayValidator<Character, Character[]> asBoxed()
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