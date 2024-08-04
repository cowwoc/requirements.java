/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ListAndSorted;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.ListValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @param <T> the type of the list
 * @param <E> the type of elements in the list
 */
public final class ListValidatorImpl<T extends List<E>, E>
	extends AbstractCollectionValidator<ListValidator<T, E>, T, E>
	implements ListValidator<T, E>
{
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
	 */
	public ListValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Pluralizer pluralizer, Map<String, Object> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, pluralizer, context, failures);
	}

	@Override
	public ListValidator<T, E> isSorted(Comparator<E> comparator)
	{
		scope.getInternalValidators().requireThat(comparator, "comparator").isNotNull();
		if (value.isNull())
			onNull();
		AtomicReference<MaybeUndefined<ListAndSorted<E>>> listAndSorted = new AtomicReference<>(
			MaybeUndefined.undefined());
		switch (value.test(value ->
		{
			List<E> sorted = value.stream().sorted(comparator).toList();
			if (value.equals(sorted))
				return true;
			listAndSorted.setPlain(MaybeUndefined.defined(new ListAndSorted<>(value, sorted)));
			return false;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.isSorted(this, listAndSorted.getPlain()).toString());
		}
		return this;
	}
}