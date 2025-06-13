/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Difference;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.component.ArrayComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Validates the state of an array.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the array
 * @param <E> the type of elements in the array
 */
public abstract class AbstractArrayValidator<S, T, E>
	extends AbstractObjectValidator<S, T>
	implements ValidatorComponent<S, T>,
	ArrayComponent<S, T, E>
{
	// REMINDER: For primitive arrays, E = Byte but A = byte[].
	// This is because type parameters cannot contain primitives such as "byte", but byte[] is considered to be
	// an object.
	private Set<E> valueAsSet;

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
	public AbstractArrayValidator(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public S isEmpty()
	{
		if (value.validationFailed(v -> getLength(v) == 0))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isEmptyFailed(this).toString());
		}
		return self();
	}

	/**
	 * @param array an array
	 * @return the length of the array
	 */
	protected abstract int getLength(T array);

	/**
	 * @param array an array
	 * @return the {@code List} representation of the array
	 */
	protected abstract List<E> asList(T array);

	/**
	 * @param array   an array
	 * @param element a value
	 * @return {@code true} if an array contains {@code element}
	 */
	protected abstract boolean contains(T array, E element);

	/**
	 * @param value an array
	 * @return the duplicate values in the array
	 */
	protected abstract Set<E> getDuplicates(T value);

	@Override
	public S isNotEmpty()
	{
		if (value.validationFailed(v -> getLength(v) != 0))
		{
			failOnNull();
			addIllegalArgumentException(CollectionMessages.isNotEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public S contains(E expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsImpl(expected, null);
	}

	@Override
	public S contains(E expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsImpl(expected, name);
	}

	private S containsImpl(E expected, String name)
	{
		if (value.validationFailed(v -> contains(v, expected)))
		{
			failOnNull();
			addIllegalArgumentException(CollectionMessages.containsFailed(this, name, expected).toString());
		}
		return self();
	}

	@Override
	public S doesNotContain(E unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public S doesNotContain(E unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainImpl(unwanted, name);
	}

	private S doesNotContainImpl(E unwanted, String name)
	{
		if (value.validationFailed(v -> !contains(v, unwanted)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainFailed(this, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public <C extends Collection<E>> S containsExactly(C expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsExactlyImpl(expected, null);
	}

	@Override
	public <C extends Collection<E>> S containsExactly(C expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsExactlyImpl(expected, name);
	}

	private <C extends Collection<E>> S containsExactlyImpl(C expected, String name)
	{
		Difference<E> difference = value.nullToInvalid().map(
			v -> Difference.actualVsOther(asList(v), expected)).or(null);
		if (difference == null || !difference.areTheSame())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsExactlyFailed(this, difference, name, expected, Pluralizer.ELEMENT).
					toString());
		}
		return self();
	}

	@Override
	public S containsExactly(T expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsExactly(asList(expected));
	}

	@Override
	public S containsExactly(T expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsExactly(asList(expected), name);
	}

	/**
	 * @param value the array being validated
	 * @return the set representation of the value
	 */
	private Set<E> getValueAsSet(T value)
	{
		// Lazy-initialize the set because this is an expensive operation for large arrays
		if (valueAsSet == null)
			valueAsSet = new HashSet<>(asList(value));
		return valueAsSet;
	}

	@Override
	public <C extends Collection<E>> S doesNotContainExactly(C unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactlyImpl(unwanted, null);
	}

	@Override
	public <C extends Collection<E>> S doesNotContainExactly(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		scope.getInternalValidators().requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, name);
	}

	private <C extends Collection<E>> S doesNotContainExactlyImpl(C unwanted, String name)
	{
		Difference<E> difference = value.nullToInvalid().map(
			v -> Difference.actualVsOther(asList(v), unwanted)).or(null);
		if (difference == null || !difference.areDifferent())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainExactlyFailed(this, name, unwanted, Pluralizer.ELEMENT).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainExactly(T unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactly(asList(unwanted), "unwanted");
	}

	@Override
	public S doesNotContainExactly(T unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainExactly(asList(unwanted), name);
	}

	@Override
	public <C extends Collection<E>> S containsAny(C expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAnyImpl(expected, null);
	}

	@Override
	public <C extends Collection<E>> S containsAny(C expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, name);
	}

	private <C extends Collection<E>> S containsAnyImpl(C expected, String name)
	{
		if (value.validationFailed(v -> !Collections.disjoint(getValueAsSet(v), expected)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsAnyFailed(this, name, expected, Pluralizer.ELEMENT).toString());
		}
		return self();
	}

	@Override
	public S containsAny(T expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAny(asList(expected));
	}

	@Override
	public S containsAny(T expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAny(asList(expected), name);
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAny(C unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAnyImpl(unwanted, null);
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAny(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, name);
	}

	private <C extends Collection<E>> S doesNotContainAnyImpl(C unwanted, String name)
	{
		Difference<E> difference = value.nullToInvalid().map(
			v -> Difference.actualVsOther(asList(v), unwanted)).or(null);
		if (difference == null || !difference.common().isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAnyFailed(this, difference, name, unwanted, Pluralizer.ELEMENT).
					toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAny(T unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAny(asList(unwanted), "unwanted");
	}

	@Override
	public S doesNotContainAny(T unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAny(asList(unwanted), name);
	}

	@Override
	public <C extends Collection<E>> S containsAll(C expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAllImpl(expected, null);
	}

	@Override
	public <C extends Collection<E>> S containsAll(C expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, name);
	}

	private <C extends Collection<E>> S containsAllImpl(C expected, String name)
	{
		Difference<E> difference = value.nullToInvalid().map(
			v -> Difference.actualVsOther(asList(v), expected)).or(null);
		if (difference == null || !difference.onlyInOther().isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsAllFailed(this, difference, name, expected, Pluralizer.ELEMENT).
					toString());
		}
		return self();
	}

	@Override
	public S containsAll(T expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAll(asList(expected));
	}

	@Override
	public S containsAll(T expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAll(asList(expected), name);
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAll(C unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAllImpl(unwanted, null);
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAll(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, name);
	}

	private <C extends Collection<E>> S doesNotContainAllImpl(C unwanted, String name)
	{
		if (value.validationFailed(v -> !getValueAsSet(v).containsAll(unwanted)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAllFailed(this, name, unwanted, Pluralizer.ELEMENT).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAll(T unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAll(asList(unwanted));
	}

	@Override
	public S doesNotContainAll(T unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAll(asList(unwanted), name);
	}

	@Override
	public S doesNotContainDuplicates()
	{
		Set<E> duplicates = value.nullToInvalid().
			map(v -> com.github.cowwoc.requirements10.java.internal.util.Collections.getDuplicates(asList(v)))
			.or(null);
		if (duplicates == null || !duplicates.isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainDuplicatesFailed(this, duplicates, Pluralizer.ELEMENT).
					toString());
		}
		return self();
	}

	@Override
	public S isSorted(Comparator<E> comparator)
	{
		ValidationTarget<List<E>> sorted = value.map(v ->
		{
			List<E> valueAsList = asList(v);
			List<E> temp = new ArrayList<>(valueAsList);
			temp.sort(comparator);
			if (temp.equals(valueAsList))
			{
				// An empty value indicates that the value is already sorted
				return List.of();
			}
			return temp;
		});
		if (sorted.validationFailed(List::isEmpty))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.isSortedFailed(this, sorted.or(null)).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		failOnNull();
		return new ObjectSizeValidatorImpl(scope, configuration, this, name + ".length()",
			value.nullToInvalid().map(this::getLength), Pluralizer.ELEMENT, context, failures);
	}
}