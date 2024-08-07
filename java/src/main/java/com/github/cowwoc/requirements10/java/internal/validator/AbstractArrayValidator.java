/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.CollectionAndDifference;
import com.github.cowwoc.requirements10.java.internal.util.CollectionAndDuplicates;
import com.github.cowwoc.requirements10.java.internal.util.Difference;
import com.github.cowwoc.requirements10.java.internal.util.ListAndSorted;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
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
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Validates the state of a primitive value.
 */
public abstract class AbstractArrayValidator<S, T, E>
	extends AbstractObjectValidator<S, T>
	implements ValidatorComponent<S, T>,
	ArrayComponent<S, T, E>
{
	// REMINDER: For primitive arrays, E = Byte but A = byte[].
	// This is because type parameters cannot contain primitives, byte is a primitive but byte[] is considered
	// an object.
	private Set<E> valueAsSet;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public AbstractArrayValidator(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public S isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> getLength(value) == 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(this).toString());
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
		if (value.isNull())
			onNull();
		switch (value.test(value -> getLength(value) != 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(this).toString());
		}
		return self();
	}

	@Override
	public S contains(E expected)
	{
		return containsImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public S contains(E expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, MaybeUndefined.defined(name));
	}

	private S containsImpl(E expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> contains(value, expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsValue(this, name, expected).toString());
		}
		return self();
	}

	@Override
	public S doesNotContain(E unwanted)
	{
		return doesNotContainImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public S doesNotContain(E unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S doesNotContainImpl(E unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !this.contains(value, unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainValue(this, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public <C extends Collection<E>> S containsExactly(C expected)
	{
		return containsExactlyImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S containsExactly(C expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsExactlyImpl(expected, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S containsExactlyImpl(C expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(asList(value), expected);
			if (difference.areTheSame())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsExactly(this, valueAndDifference.getPlain(), name, expected,
					Pluralizer.ELEMENT).toString());
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
		return doesNotContainExactlyImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S doesNotContainExactly(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S doesNotContainExactlyImpl(C unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> Difference.actualVsOther(asList(value), unwanted).areDifferent()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainExactly(this, name, unwanted, Pluralizer.ELEMENT).toString());
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
		return containsAnyImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S containsAny(C expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S containsAnyImpl(C expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !Collections.disjoint(getValueAsSet(value), expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsAny(this, value.mapNotNull(this::asList), name, expected,
					Pluralizer.ELEMENT).toString());
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
		return doesNotContainAnyImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAny(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S doesNotContainAnyImpl(C unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(asList(value), unwanted);
			if (difference.common().isEmpty())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(
				new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainAny(this, valueAndDifference.getPlain(), name, unwanted,
					Pluralizer.ELEMENT).toString());
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
		return containsAllImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S containsAll(C expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S containsAllImpl(C expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(asList(value), expected);
			if (difference.onlyInOther().isEmpty())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(
				new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsAll(this, valueAndDifference.getPlain(), name, expected,
					Pluralizer.ELEMENT).toString());
		}
		return self();
	}

	@Override
	public S containsAll(T expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAll(asList(expected), null);
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
		return doesNotContainAllImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public <C extends Collection<E>> S doesNotContainAll(C unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, MaybeUndefined.defined(name));
	}

	private <C extends Collection<E>> S doesNotContainAllImpl(C unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !getValueAsSet(value).containsAll(unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(this, value, name, unwanted, Pluralizer.ELEMENT).toString());
		}
		return self();
	}

	@Override
	public S doesNotContainAll(T unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAll(asList(unwanted), "unwanted");
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
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDuplicates<T, E>>> valueAndDuplicates = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Set<E> duplicates = getDuplicates(value);
			if (duplicates.isEmpty())
				return true;
			valueAndDuplicates.setPlain(MaybeUndefined.defined(new CollectionAndDuplicates<>(value, duplicates)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainDuplicates(this, valueAndDuplicates.getPlain(), Pluralizer.ELEMENT).
					toString());
		}
		return self();
	}

	@Override
	public S isSorted(Comparator<E> comparator)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<ListAndSorted<E>>> valueAndSorted = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			List<E> valueAsList = asList(value);
			List<E> sortedList = new ArrayList<>(valueAsList);
			sortedList.sort(comparator);
			valueAndSorted.setPlain(MaybeUndefined.defined(new ListAndSorted<>(valueAsList, sortedList)));
			return valueAsList.equals(sortedList);
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.isSorted(this, valueAndSorted.getPlain()).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		if (value.isNull())
			onNull();
		return new ObjectSizeValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(value -> new ObjectAndSize(value, getLength(value))),
			name + ".length()", Pluralizer.ELEMENT, context, failures);
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		if (value.isNull())
			onNull();
		return new CollectionValidatorImpl<>(scope, configuration, name + ".asCollection()",
			value.nullToUndefined().mapDefined(this::asList), Pluralizer.ELEMENT, context, failures);
	}

	@Override
	public ListValidator<List<E>, E> asList()
	{
		if (value.isNull())
			onNull();
		return new ListValidatorImpl<>(scope, configuration, name + ".asList()",
			value.nullToUndefined().mapDefined(this::asList), Pluralizer.ELEMENT, context, failures);
	}
}