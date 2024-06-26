package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleArrayValidator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrimitiveDoubleArrayValidatorImpl
	extends AbstractArrayValidator<PrimitiveDoubleArrayValidator, Double, double[]>
	implements PrimitiveDoubleArrayValidator
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
	public PrimitiveDoubleArrayValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		double[] value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected int getLength(double[] array)
	{
		return array.length;
	}

	@Override
	protected Set<Double> getDuplicates(double[] value)
	{
		Set<Double> unique = new HashSet<>(value.length);
		Set<Double> duplicates = new HashSet<>(value.length);
		for (double element : value)
		{
			if (!unique.add(element))
			{
				duplicates.add(element);
			}
		}
		return duplicates;
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(double expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(Double expected)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((double) expected);
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(double expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator contains(Double expected, String name)
	{
		scope.getInternalValidators().requireThat(expected, "Expected").isNotNull();
		return contains((double) expected, name);
	}

	private PrimitiveDoubleArrayValidator containsImpl(double expected, String name)
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
	public PrimitiveDoubleArrayValidator doesNotContain(double unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PrimitiveDoubleArrayValidator doesNotContain(Double unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "Expected").isNotNull();
		return doesNotContain((double) unwanted, null);
	}

	@Override
	public PrimitiveDoubleArrayValidator doesNotContain(double unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return doesNotContainImpl(unwanted, name);
	}

	@Override
	public PrimitiveDoubleArrayValidator doesNotContain(Double unwanted, String name)
	{
		scope.getInternalValidators().requireThat(unwanted, "Expected").isNotNull();
		return doesNotContain((double) unwanted, name);
	}

	private PrimitiveDoubleArrayValidator doesNotContainImpl(double unwanted, String name)
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
	public PrimitiveDoubleArrayValidator isSorted()
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
	public PrimitiveDoubleArrayValidator isSorted(Comparator<Double> comparator)
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
	protected boolean isSorted(double[] array, Comparator<Double> comparator)
	{
		return Arrays.isSorted(Arrays.asList(array), comparator);
	}

	@Override
	protected List<Double> sorted(double[] array, Comparator<Double> comparator)
	{
		List<Double> list = Arrays.asList(array);
		list.sort(comparator);
		return list;
	}

	@Override
	public ObjectArrayValidator<Double, Double[]> asBoxed()
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