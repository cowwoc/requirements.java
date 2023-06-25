/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Arrays;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.internal.util.Sets;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.part.CollectionPart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates the state of a collection.
 *
 * @param <S> the type of validator that the methods should return
 * @param <E> the type of elements in the collection
 * @param <T> the type of the collection
 */
public abstract class AbstractCollectionValidator<S extends CollectionPart<S, E>, E, T extends Collection<E>>
	extends AbstractObjectValidator<S, T>
	implements CollectionPart<S, E>
{
	private final Pluralizer pluralizer;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param pluralizer    the type of items in the collection
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public AbstractCollectionValidator(ApplicationScope scope, Configuration configuration,
		String name, T value, Pluralizer pluralizer, Map<String, Object> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
		assert (pluralizer != null);
		this.pluralizer = pluralizer;
	}

	@Override
	public S isEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isEmpty(scope, this, name, null, null, 0).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
		{
			// The size can change when invoked on a concurrent collection
			int size = value.size();
			if (size != 0)
			{
				addIllegalArgumentException(
					CollectionMessages.isEmpty(scope, this, name, value, name + ".size()", size).toString());
			}
		}
		return self();
	}

	@Override
	public S isNotEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		else
		{
			// The size can change when invoked on a concurrent collection
			int size = value.size();
			if (size == 0)
			{
				addIllegalArgumentException(
					CollectionMessages.isNotEmpty(scope, this, name).toString());
			}
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
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return containsImpl(expected, name);
	}

	private S containsImpl(E expected, String name)
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
		else if (!value.contains(expected))
		{
			addIllegalArgumentException(
				CollectionMessages.contains(scope, this, this.name, value, name, expected).toString());
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
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return containsExactlyImpl(expected, name);
	}

	private S containsExactlyImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsExactly(scope, this, this.name, null, null,
					null, pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			Set<E> valueAsSet = Sets.fromCollection(value);
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> missing = Sets.difference(expectedAsSet, valueAsSet);
			Set<E> unwanted = Sets.difference(valueAsSet, expectedAsSet);
			if (!missing.isEmpty() || !unwanted.isEmpty())
			{
				MessageBuilder message = CollectionMessages.containsExactly(scope, this, this.name, value, name,
						expected, pluralizer).
					addContext(missing, "Missing").
					addContext(unwanted, "Unwanted");
				addIllegalArgumentException(message.toString());
			}
		}
		return self();
	}

	@Override
	public S containsExactly(E[] expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsExactly(Arrays.asList(expected), null);
	}

	@Override
	public S containsExactly(E[] expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		internalValidator.requireThat(expected, name).isNotNull();
		return containsExactly(Arrays.asList(expected), name);
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAnyImpl(expected, null);
	}

	@Override
	public S containsAny(Collection<E> expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, name);
	}

	private S containsAnyImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsAny(scope, this, this.name, null, null, null,
					pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (Collections.disjoint(value, expected))
		{
			addIllegalArgumentException(
				CollectionMessages.containsAny(scope, this, this.name, value, name, expected, pluralizer).
					toString());
		}
		return self();
	}

	@Override
	public S containsAny(E[] expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAny(Arrays.asList(expected), null);
	}

	@Override
	public S containsAny(E[] expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAny(Arrays.asList(expected), name);
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAllImpl(expected, null);
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, name);
	}

	private S containsAllImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsAll(scope, this, this.name, null, null, null, pluralizer).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!value.containsAll(expected))
		{
			Set<E> valueAsSet = Sets.fromCollection(value);
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> missing = Sets.difference(expectedAsSet, valueAsSet);

			MessageBuilder message = CollectionMessages.containsAll(scope, this, this.name, value, name,
					expected, pluralizer).
				addContext(missing, "Missing");
			addIllegalArgumentException(message.toString());
		}
		return self();
	}

	@Override
	public S containsAll(E[] expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAll(Arrays.asList(expected), null);
	}

	@Override
	public S containsAll(E[] expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAll(Arrays.asList(expected), name);
	}

	@Override
	public S doesNotContain(E unwanted)
	{
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public S doesNotContain(E unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainImpl(unwanted, name);
	}

	private S doesNotContainImpl(E unwanted, String name)
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
		else if (value.contains(unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContain(scope, this, this.name, value, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainExactly(Collection<E> unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactlyImpl(unwanted, null);
	}

	@Override
	public S doesNotContainExactly(Collection<E> unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, name);
	}

	private S doesNotContainExactlyImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainExactly(scope, this, this.name, null, null, null,
					pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			Set<E> valueAsSet = Sets.fromCollection(value);
			Set<E> unwantedAsSet = Sets.fromCollection(unwanted);
			Set<E> missingElements = Sets.difference(unwantedAsSet, valueAsSet);
			Set<E> unwantedElements = Sets.difference(valueAsSet, unwantedAsSet);
			if (missingElements.isEmpty() && unwantedElements.isEmpty())
			{
				addIllegalArgumentException(
					CollectionMessages.doesNotContainExactly(scope, this, this.name, value, name, unwanted,
						pluralizer).toString());
			}
		}
		return self();
	}

	@Override
	public S doesNotContainExactly(E[] unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactly(Arrays.asList(unwanted), null);
	}

	@Override
	public S doesNotContainExactly(E[] unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainExactly(Arrays.asList(unwanted), name);
	}

	@Override
	public S doesNotContainAny(Collection<E> unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAnyImpl(unwanted, null);
	}

	@Override
	public S doesNotContainAny(Collection<E> unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, name);
	}

	private S doesNotContainAnyImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAny(scope, this, this.name, null, null,
					null, pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Collections.disjoint(value, unwanted))
		{
			Set<E> valueAsSet = Sets.fromCollection(value);
			Set<E> unwantedAsSet = Sets.fromCollection(unwanted);
			Set<E> unwantedElements = Sets.difference(valueAsSet, unwantedAsSet);

			MessageBuilder message = CollectionMessages.doesNotContainAny(scope, this,
					this.name, value, name, unwanted, pluralizer).
				addContext(unwantedElements, "Unwanted");
			addIllegalArgumentException(message.toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAny(E[] unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAny(Arrays.asList(unwanted), null);
	}

	@Override
	public S doesNotContainAny(E[] unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		isNotEqualTo(this.name, "the name of the value");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAny(Arrays.asList(unwanted), name);
	}

	@Override
	public S doesNotContainAll(Collection<E> unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAllImpl(unwanted, null);
	}

	@Override
	public S doesNotContainAll(Collection<E> unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, name);
	}

	private S doesNotContainAllImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(scope, this, this.name, null, null, null,
					pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.containsAll(unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(scope, this, this.name, value, name, unwanted,
					pluralizer).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAll(E[] unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAll(Arrays.asList(unwanted), null);
	}

	@Override
	public S doesNotContainAll(E[] unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		isNotEqualTo(this.name, "the name of the value");
		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAll(Arrays.asList(unwanted), name);
	}

	@Override
	public S doesNotContainDuplicates()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainDuplicates(scope, this, this.name, null, Set.of(),
					pluralizer).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			if (value instanceof Set)
				return self();
			int size = value.size();
			Set<E> unique = new HashSet<>(size);
			Set<E> duplicates = new HashSet<>(size);
			for (E element : value)
			{
				if (!unique.add(element))
					duplicates.add(element);
			}
			if (!duplicates.isEmpty())
			{
				addIllegalArgumentException(
					CollectionMessages.doesNotContainDuplicates(scope, this, this.name, value, duplicates,
						pluralizer).toString());
			}
		}
		return self();
	}

	@Override
	public S containsSameNullity()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsSameNullity(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			int numberOfNulls = 0;
			for (E element : value)
				if (element == null)
					++numberOfNulls;
			if (numberOfNulls != value.size())
			{
				addIllegalArgumentException(CollectionMessages.containsSameNullity(scope, this,
					this.name, value).toString());
			}
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl size()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".size()", 0,
				null, pluralizer);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".size()", 0,
				null, pluralizer);
		}
		return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".size()", value.size(),
			value, pluralizer);
	}

	@Override
	public ObjectArrayValidator<E, E[]> asArray(Class<E> type)
	{
		if (hasFailed())
			return new ObjectArrayValidatorImpl<>(scope, this, name + ".asArray()", null);
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new ObjectArrayValidatorImpl<>(scope, this, name + ".asArray()", null);
		}
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(type, value.size());
		return new ObjectArrayValidatorImpl<>(scope, this, name, value.toArray(array));
	}

	@Override
	public ListValidator<E, List<E>> asList()
	{
		if (hasFailed())
			return new ListValidatorImpl<>(scope, this, name + ".asList()", null, pluralizer);
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new ListValidatorImpl<>(scope, this, name + ".asList()", null, pluralizer);
		}
		return new ListValidatorImpl<>(scope, this, name, new ArrayList<>(value), pluralizer);
	}
}