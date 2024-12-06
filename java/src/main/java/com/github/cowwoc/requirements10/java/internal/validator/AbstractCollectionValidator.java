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
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.component.CollectionComponent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Validates the state of a collection.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the collection
 * @param <E> the type of elements in the collection
 */
public abstract class AbstractCollectionValidator<S, T extends Collection<E>, E>
	extends AbstractObjectValidator<S, T>
	implements CollectionComponent<S, E>
{
	private final Pluralizer pluralizer;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param pluralizer    the type of items in the collection
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public AbstractCollectionValidator(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Pluralizer pluralizer, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
		assert pluralizer != null;
		this.pluralizer = pluralizer;
	}

	@Override
	public S isEmpty()
	{
		if (value.validationFailed(Collection::isEmpty))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public S isNotEmpty()
	{
		if (value.validationFailed(v -> !v.isEmpty()))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isNotEmptyFailed(this).toString());
		}
		return self();
	}

	@Override
	public S contains(E expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public S contains(E expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	@SuppressWarnings("PMD.AvoidCatchingNPE")
	private S containsImpl(E expected, String name)
	{
		if (value.validationFailed(v ->
		{
			try
			{
				return v.contains(expected);
			}
			catch (NullPointerException e)
			{
				if (expected == null)
				{
					// The specified element is null, and this collection does not permit null elements
					assert (v != null);
					return false;
				}
				throw e;
			}
		}))
		{
			failOnNull();
			addIllegalArgumentException(CollectionMessages.containsFailed(this, name, expected).toString());
		}
		return self();
	}

	@Override
	public S doesNotContain(E unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public S doesNotContain(E unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainImpl(unwanted, name);
	}

	@SuppressWarnings("PMD.AvoidCatchingNPE")
	private S doesNotContainImpl(E unwanted, String name)
	{
		if (value.validationFailed(v ->
		{
			try
			{
				return !v.contains(unwanted);
			}
			catch (NullPointerException e)
			{
				// The specified element is null, and this collection does not permit null elements
				assert (v != null);
				return true;
			}
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainFailed(this, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		return containsExactlyImpl(expected, null);
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsExactlyImpl(expected, name);
	}

	private S containsExactlyImpl(Collection<E> expected, String name)
	{
		Difference<E> difference = value.nullToInvalid().
			map(v -> Difference.actualVsOther(v, expected)).or(null);
		if (difference == null || !difference.areTheSame())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsExactlyFailed(this, difference, name, expected, pluralizer).toString());
		}
		return self();
	}

	@Override
	public S containsExactly(E[] expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsExactly(Arrays.asList(expected), "expected");
	}

	@Override
	public S containsExactly(E[] expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsExactly(Arrays.asList(expected), name);
	}

	@Override
	public S doesNotContainExactly(Collection<E> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactlyImpl(unwanted, null);
	}

	@Override
	public S doesNotContainExactly(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, name);
	}

	private S doesNotContainExactlyImpl(Collection<E> unwanted, String name)
	{
		Difference<E> difference = value.nullToInvalid().
			map(v -> Difference.actualVsOther(v, unwanted)).or(null);
		if (difference == null || !difference.areDifferent())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainExactlyFailed(this, name, unwanted, pluralizer).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainExactly(E[] unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactly(Arrays.asList(unwanted), "unwanted");
	}

	@Override
	public S doesNotContainExactly(E[] unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainExactly(Arrays.asList(unwanted), name);
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAnyImpl(expected, null);
	}

	@Override
	public S containsAny(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, name);
	}

	private S containsAnyImpl(Collection<E> expected, String name)
	{
		if (value.validationFailed(v -> !Collections.disjoint(v, expected)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsAnyFailed(this, name, expected, pluralizer).toString());
		}
		return self();
	}

	@Override
	public S containsAny(E[] expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAny(Arrays.asList(expected));
	}

	@Override
	public S containsAny(E[] expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAny(Arrays.asList(expected), name);
	}

	@Override
	public S doesNotContainAny(Collection<E> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAnyImpl(unwanted, null);
	}

	@Override
	public S doesNotContainAny(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, name);
	}

	private S doesNotContainAnyImpl(Collection<E> unwanted, String name)
	{
		Difference<E> difference = value.nullToInvalid().
			map(v -> Difference.actualVsOther(v, unwanted)).or(null);
		if (difference == null || !difference.common().isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAnyFailed(this, difference, name, unwanted, pluralizer).
					toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAny(E[] unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAny(Arrays.asList(unwanted), "unwanted");
	}

	@Override
	public S doesNotContainAny(E[] unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAny(Arrays.asList(unwanted), name);
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAllImpl(expected, null);
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, name);
	}

	private S containsAllImpl(Collection<E> expected, String name)
	{
		Difference<E> difference = value.nullToInvalid().
			map(v -> Difference.actualVsOther(v, expected)).or(null);
		if (difference == null || !difference.onlyInOther().isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsAllFailed(this, difference, name, expected, pluralizer).
					toString());
		}
		return self();
	}

	@Override
	public S containsAll(E[] expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAll(Arrays.asList(expected));
	}

	@Override
	public S containsAll(E[] expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAll(Arrays.asList(expected), name);
	}

	@Override
	public S doesNotContainAll(Collection<E> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAllImpl(unwanted, null);
	}

	@Override
	public S doesNotContainAll(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, name);
	}

	private S doesNotContainAllImpl(Collection<E> unwanted, String name)
	{
		if (value.validationFailed(v -> !v.containsAll(unwanted)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAllFailed(this, name, unwanted, pluralizer).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAll(E[] unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAll(Arrays.asList(unwanted), "unwanted");
	}

	@Override
	public S doesNotContainAll(E[] unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAll(Arrays.asList(unwanted), name);
	}

	@Override
	public S doesNotContainDuplicates()
	{
		Set<E> duplicates = value.nullToInvalid().
			map(com.github.cowwoc.requirements10.java.internal.util.Collections::getDuplicates).or(null);
		if (duplicates == null || !duplicates.isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.doesNotContainDuplicatesFailed(this, duplicates, pluralizer).
					toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		failOnNull();
		return new ObjectSizeValidatorImpl(scope, configuration, this, name + ".size()",
			value.nullToInvalid().map(Collection::size), pluralizer, context, failures);
	}
}