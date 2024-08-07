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
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.component.CollectionComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
	 * @param value         the value
	 * @param pluralizer    the type of items in the collection
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public AbstractCollectionValidator(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Pluralizer pluralizer, Map<String, Object> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
		assert pluralizer != null;
		this.pluralizer = pluralizer;
	}

	@Override
	public S isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Collection::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(this).toString());
		}
		return self();
	}

	@Override
	public S isNotEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isEmpty()))
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
		switch (value.test(value -> value.contains(expected)))
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
		switch (value.test(value -> !value.contains(unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainValue(this, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		return containsExactlyImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsExactlyImpl(expected, MaybeUndefined.defined(name));
	}

	private S containsExactlyImpl(Collection<E> expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(value, expected);
			if (difference.areTheSame())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsExactly(this, valueAndDifference.getPlain(), name, expected, pluralizer).
					toString());
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
		return doesNotContainExactlyImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public S doesNotContainExactly(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainExactlyImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S doesNotContainExactlyImpl(Collection<E> unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> Difference.actualVsOther(value, unwanted).areDifferent()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainExactly(this, name, unwanted, pluralizer).toString());
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
		return containsAnyImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public S containsAny(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAnyImpl(expected, MaybeUndefined.defined(name));
	}

	private S containsAnyImpl(Collection<E> expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !Collections.disjoint(value, expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsAny(this, value, name, expected, pluralizer).toString());
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
		return doesNotContainAnyImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public S doesNotContainAny(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAnyImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S doesNotContainAnyImpl(Collection<E> unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(value, unwanted);
			if (difference.common().isEmpty())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(
				new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainAny(this, valueAndDifference.getPlain(), name, unwanted,
					pluralizer).toString());
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
		return containsAllImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, name).isNotNull();
		return containsAllImpl(expected, MaybeUndefined.defined(name));
	}

	private S containsAllImpl(Collection<E> expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDifference<T, E>>> valueAndDifference = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			Difference<E> difference = Difference.actualVsOther(value, expected);
			if (difference.onlyInOther().isEmpty())
				return true;
			valueAndDifference.setPlain(MaybeUndefined.defined(
				new CollectionAndDifference<>(value, difference)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsAll(this, valueAndDifference.getPlain(), name, expected, pluralizer).
					toString());
		}
		return self();
	}

	@Override
	public S containsAll(E[] expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAll(Arrays.asList(expected), "expected");
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
		return doesNotContainAllImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public S doesNotContainAll(Collection<E> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		return doesNotContainAllImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S doesNotContainAllImpl(Collection<E> unwanted, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.containsAll(unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.doesNotContainAll(this, value, name, unwanted, pluralizer).toString());
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
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<CollectionAndDuplicates<T, E>>> valueAndDuplicates = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			if (value instanceof Set<?>)
				return true;
			int size = value.size();
			Set<E> unique = new HashSet<>(size);
			Set<E> duplicates = new HashSet<>(size);
			for (E element : value)
			{
				if (!unique.add(element))
					duplicates.add(element);
			}
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
	public S containsSameNullity()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int numberOfNulls = 0;
			for (E element : value)
				if (element == null)
					++numberOfNulls;
			return (numberOfNulls == value.size());
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSameNullity(this).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator size()
	{
		if (value.isNull())
			onNull();
		return new ObjectSizeValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(value -> new ObjectAndSize(value, value.size())),
			name + ".size()", Pluralizer.ELEMENT, context, failures);
	}

	@Override
	public ObjectArrayValidator<E[], E> asArray(Class<E> type)
	{
		if (value.isNull())
			onNull();
		MaybeUndefined<E[]> array = value.nullToUndefined().mapDefined(v ->
		{
			@SuppressWarnings("unchecked")
			E[] target = (E[]) Array.newInstance(type, v.size());
			return v.toArray(target);
		});
		return new ObjectArrayValidatorImpl<>(scope, configuration, name + ".asArray()", array, context,
			failures);
	}

	@Override
	public ListValidator<List<E>, E> asList()
	{
		if (value.isNull())
			onNull();
		return new ListValidatorImpl<>(scope, configuration, name + ".asList()",
			value.nullToUndefined().mapDefined(ArrayList::new), pluralizer, context, failures);
	}
}