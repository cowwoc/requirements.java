/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaValidators;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.ListValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @param <E> the type of elements in the list
 * @param <T> the type of the list
 */
public final class ListValidatorImpl<E, T extends List<E>>
	extends AbstractCollectionValidator<ListValidator<E, T>, E, T>
	implements ListValidator<E, T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param pluralizer    (optional) the type of items in the collection
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ListValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Pluralizer pluralizer, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, pluralizer, context, failures);
	}

	@Override
	public ListValidator<E, T> isSorted(Comparator<E> comparator)
	{
		JavaValidators internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(comparator, "comparator").isNotNull();

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
			List<E> sorted = value.stream().sorted(comparator).toList();
			if (!value.equals(sorted))
			{
				addIllegalArgumentException(
					CollectionMessages.isSorted(scope, this, this.name, value, sorted).toString());
			}
		}
		return this;
	}
}