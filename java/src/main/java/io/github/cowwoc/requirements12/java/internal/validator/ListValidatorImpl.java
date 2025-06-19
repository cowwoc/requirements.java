/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.message.CollectionMessages;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.Pluralizer;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.validator.ListValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	 * @param value         the value being validated
	 * @param pluralizer    the type of items in the collection
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public ListValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Pluralizer pluralizer, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, pluralizer, context, failures);
	}

	@Override
	public ListValidator<T, E> isSorted(Comparator<E> comparator)
	{
		scope.getInternalValidators().requireThat(comparator, "comparator").isNotNull();

		List<E> expected = value.map(v ->
		{
			List<E> sorted = new ArrayList<>(v);
			sorted.sort(comparator);
			return sorted;
		}).or(null);
		if (value.validationFailed(v -> v.equals(expected)))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.isSortedFailed(this, expected).toString());
		}
		return this;
	}
}