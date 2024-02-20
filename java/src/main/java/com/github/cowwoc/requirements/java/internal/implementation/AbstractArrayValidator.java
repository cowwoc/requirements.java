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
import com.github.cowwoc.requirements.java.internal.util.Collections;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.internal.util.Sets;
import com.github.cowwoc.requirements.java.type.part.ArrayPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <S> the type of validator that the methods should return
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public abstract class AbstractArrayValidator<S extends ArrayPart<S, E, A>, E, A>
	extends AbstractObjectValidator<S, A>
	implements ArrayPart<S, E, A>
{
	private static final Pluralizer PLURALIZER = Pluralizer.ELEMENT;
	// REMINDER: For primitive arrays, E = Byte but A = byte[].
	// This is because type parameters cannot contain primitives, byte is a primitive but byte[] is considered
	// an object.
	private Set<E> valueAsSet;

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
	public AbstractArrayValidator(ApplicationScope scope, Configuration configuration, String name,
		A value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
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
			// The length can change when invoked on a concurrent collection
			int length = getLength(value);
			if (length != 0)
			{
				addIllegalArgumentException(
					CollectionMessages.isEmpty(scope, this, name, value, name + ".length", length).toString());
			}
		}
		return self();
	}

	/**
	 * @param array an array
	 * @return the length of the array
	 */
	protected abstract int getLength(A array);

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
		else if (getLength(value) == 0)
		{
			addIllegalArgumentException(
				CollectionMessages.isNotEmpty(scope, this, name).toString());
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
			isNotEqualTo(this.name, "the same name as the value");
		return containsExactlyImpl(expected, name);
	}

	private S containsExactlyImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsExactly(scope, this, this.name, null, null, null,
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			List<E> unwanted = new ArrayList<>();
			List<E> missing = new ArrayList<>();
			Collections.difference(asList(value), expected, unwanted, missing);

			if (!missing.isEmpty() || !unwanted.isEmpty())
			{
				MessageBuilder message = CollectionMessages.containsExactly(scope, this, this.name, value, name,
						expected, PLURALIZER).
					putContext(missing, "Missing").
					putContext(unwanted, "Unwanted");
				addIllegalArgumentException(message.toString());
			}
		}
		return self();
	}

	/**
	 * @param array an array
	 * @return a List representation of the array
	 */
	protected List<E> asList(A array)
	{
		@SuppressWarnings("unchecked")
		E[] elements = (E[]) array;
		return Arrays.asList(elements);
	}

	@Override
	public S containsExactly(A expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsExactly(asList(expected));
	}

	@Override
	public S containsExactly(A expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsExactly(asList(expected), name);
	}

	protected Set<E> getValueAsSet()
	{
		// Lazy-initialize the set because this is an expensive operation for large arrays
		if (valueAsSet == null)
			valueAsSet = new HashSet<>(asList(value));
		return valueAsSet;
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
			isNotEqualTo(this.name, "the same name as the value");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, name);
	}

	private S containsAnyImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsAny(scope, this, this.name, null, null, null,
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (Collections.containsAny(getValueAsSet(), expected))
		{
			addIllegalArgumentException(
				CollectionMessages.containsAny(scope, this, this.name, value, name, expected,
					PLURALIZER).toString());
		}
		return self();
	}

	@Override
	public S containsAny(A expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAny(asList(expected));
	}

	@Override
	public S containsAny(A expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		internalValidator.requireThat(expected, name).isNotNull();
		return containsAny(asList(expected), name);
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
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, name);
	}

	private S containsAllImpl(Collection<E> expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.containsAll(scope, this, this.name, null, null, null, PLURALIZER).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!getValueAsSet().containsAll(expected))
		{
			List<E> unwantedElements = new ArrayList<>();
			List<E> missingElements = new ArrayList<>();
			Collections.difference(asList(value), expected, unwantedElements, missingElements);

			MessageBuilder message = CollectionMessages.containsAll(scope, this, this.name, value, name,
					expected, PLURALIZER).
				putContext(missingElements, "Missing");
			addIllegalArgumentException(message.toString());
		}
		return self();
	}

	@Override
	public S containsAll(A expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		return containsAll(asList(expected), null);
	}

	@Override
	public S containsAll(A expected, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(expected, name).isNotNull();
		return containsAll(asList(expected), name);
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
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, name);
	}

	private S doesNotContainExactlyImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainExactly(scope, this, this.name, null, null, null,
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			List<E> unwantedElements = new ArrayList<>();
			List<E> missingElements = new ArrayList<>();
			Collections.difference(asList(value), unwanted, unwantedElements, missingElements);

			if (missingElements.isEmpty() && unwantedElements.isEmpty())
			{
				addIllegalArgumentException(
					CollectionMessages.doesNotContainExactly(scope, this, this.name, value, name, unwanted,
						PLURALIZER).toString());
			}
		}
		return self();
	}

	public S doesNotContainExactly(A unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactly(asList(unwanted), null);
	}

	@Override
	public S doesNotContainExactly(A unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainExactly(asList(unwanted), name);
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
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, name);
	}

	private S doesNotContainAnyImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAny(scope, this, this.name, null, null, null,
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Collections.containsAny(getValueAsSet(), unwanted))
		{
			Set<E> valueAsSet = getValueAsSet();
			Set<E> unwantedAsSet = Sets.fromCollection(unwanted);
			Set<E> unwantedElements = Sets.difference(valueAsSet, unwantedAsSet);

			MessageBuilder message = CollectionMessages.doesNotContainAny(scope, this, this.name, value,
					name, unwanted, PLURALIZER).
				putContext(unwantedElements, "Unwanted");
			addIllegalArgumentException(message.toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAny(A unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAny(asList(unwanted), null);
	}

	@Override
	public S doesNotContainAny(A unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAny(asList(unwanted), name);
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
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, name);
	}

	private S doesNotContainAllImpl(Collection<E> unwanted, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(scope, this, this.name, null, null, null,
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (getValueAsSet().containsAll(unwanted))
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(scope, this, this.name, value, name, unwanted,
					PLURALIZER).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAll(A unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAll(asList(unwanted), null);
	}

	@Override
	public S doesNotContainAll(A unwanted, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		isNotEqualTo(this.name, "the name of the value");

		internalValidator.requireThat(unwanted, name).isNotNull();
		return doesNotContainAll(asList(unwanted), name);
	}

	@Override
	public S doesNotContainDuplicates()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				CollectionMessages.doesNotContainDuplicates(scope, this, this.name, null, Set.of(),
					PLURALIZER).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			if (valueAsSet != null && valueAsSet.size() == getLength(value))
				return self();
			Set<E> duplicates = getDuplicates(value);
			if (!duplicates.isEmpty())
			{
				addIllegalArgumentException(
					CollectionMessages.doesNotContainDuplicates(scope, this, this.name, value, duplicates,
						PLURALIZER).toString());
			}
		}
		return self();
	}

	/**
	 * @param value an array
	 * @return the duplicate values in the array
	 */
	protected abstract Set<E> getDuplicates(A value);

	@Override
	public S isSorted(Comparator<E> comparator)
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
		else if (!isSorted(value, comparator))
		{
			addIllegalArgumentException(
				CollectionMessages.isSorted(scope, this, this.name, value, sorted(value, comparator)).
					toString());
		}
		return self();
	}

	/**
	 * @param array      a array
	 * @param comparator a comparator that indicates the expected order
	 * @return true if the collection is in order
	 */
	protected abstract boolean isSorted(A array, Comparator<E> comparator);

	/**
	 * @param array      a array
	 * @param comparator a comparator that indicates the expected order
	 * @return a sorted copy of the array
	 */
	protected abstract List<E> sorted(A array, Comparator<E> comparator);

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl length()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".length", 0,
				value, PLURALIZER);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, this, name + ".length", 0,
				value, PLURALIZER);
		}
		PrimitiveUnsignedIntegerValidatorImpl newValidator = new PrimitiveUnsignedIntegerValidatorImpl(scope,
			this, name + ".length", getLength(value), value, PLURALIZER);
		newValidator.putContext(value, name);
		return newValidator;
	}

	@Override
	public CollectionValidatorImpl<E, Collection<E>> asCollection()
	{
		if (!failures.isEmpty())
			return new CollectionValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		return new CollectionValidatorImpl<>(scope, this, name, asList(value), Pluralizer.ELEMENT);
	}

	@Override
	public ListValidatorImpl<E, List<E>> asList()
	{
		if (!failures.isEmpty())
			return new ListValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		return new ListValidatorImpl<>(scope, this, name, asList(value), Pluralizer.ELEMENT);
	}
}