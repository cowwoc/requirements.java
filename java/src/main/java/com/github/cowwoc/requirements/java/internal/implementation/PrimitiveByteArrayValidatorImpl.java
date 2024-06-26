package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveByteArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveByteArrayValidator, Byte, byte[]>
	implements PrimitiveByteArrayValidator
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
	public PrimitiveByteArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		byte[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(byte[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Byte> getDuplicates(byte[] value)
	{
		Set<Byte> unique = new HashSet<>(value.length);
		Set<Byte> duplicates = new HashSet<>(value.length);
		for (byte element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveByteArrayValidator contains(byte expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveByteArrayValidator contains(Byte expected)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((byte) expected, null);
	}

	@Override
	public PrimitiveByteArrayValidator contains(byte expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveByteArrayValidator contains(Byte expected, String name)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((byte) expected, name);
	}

	private PrimitiveByteArrayValidator containsImpl(byte expected, String name)
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
	public PrimitiveByteArrayValidator doesNotContain(byte unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveByteArrayValidator doesNotContain(Byte unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((byte) unwanted, null);
	}

	@Override
	public PrimitiveByteArrayValidator doesNotContain(byte unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveByteArrayValidator doesNotContain(Byte unwanted, String name)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((byte) unwanted, name);
	}

	private PrimitiveByteArrayValidator doesNotContainImpl(byte unwanted, String name)
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
	public PrimitiveByteArrayValidator isSorted()
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
	public PrimitiveByteArrayValidator isSorted(Comparator<Byte> comparator)
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
	protected boolean isSorted(byte[] array, Comparator<Byte> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Byte> sorted(byte[] array, Comparator<Byte> comparator)
	{
		List<Byte> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public ObjectArrayValidator<Byte, Byte[]> asBoxed()
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