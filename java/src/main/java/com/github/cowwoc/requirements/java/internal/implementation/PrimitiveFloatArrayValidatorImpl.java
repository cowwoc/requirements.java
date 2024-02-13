package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatArrayValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveFloatArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveFloatArrayValidator, Float, float[]>
	implements PrimitiveFloatArrayValidator
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
	public PrimitiveFloatArrayValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator,
		String name, float[] value)
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
	public PrimitiveFloatArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, float[] value)
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
	private PrimitiveFloatArrayValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, float[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(float[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Float> getDuplicates(float[] value)
	{
		Set<Float> unique = new HashSet<>(value.length);
		Set<Float> duplicates = new HashSet<>(value.length);
		for (float element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveFloatArrayValidator contains(float expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveFloatArrayValidator contains(Float expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((float) expected, null);
	}

	@Override
	public PrimitiveFloatArrayValidator contains(float expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveFloatArrayValidator contains(Float expected, String name)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return contains((float) expected, name);
	}

	private PrimitiveFloatArrayValidator containsImpl(float expected, String name)
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
	public PrimitiveFloatArrayValidator doesNotContain(float unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveFloatArrayValidator doesNotContain(Float unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((float) unwanted, null);
	}

	@Override
	public PrimitiveFloatArrayValidator doesNotContain(float unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveFloatArrayValidator doesNotContain(Float unwanted, String name)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContain((float) unwanted, name);
	}

	private PrimitiveFloatArrayValidator doesNotContainImpl(float unwanted, String name)
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
	public PrimitiveFloatArrayValidator isSorted()
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
	public PrimitiveFloatArrayValidator isSorted(Comparator<Float> comparator)
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
	protected boolean isSorted(float[] array, Comparator<Float> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Float> sorted(float[] array, Comparator<Float> comparator)
	{
		List<Float> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}
}