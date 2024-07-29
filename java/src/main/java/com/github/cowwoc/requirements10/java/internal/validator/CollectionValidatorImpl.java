/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionValidatorImpl<T extends Collection<E>, E>
	extends AbstractCollectionValidator<CollectionValidator<T, E>, T, E>
	implements CollectionValidator<T, E>
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
	public CollectionValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Pluralizer pluralizer, Map<String, Object> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, pluralizer, context, failures);
	}
}