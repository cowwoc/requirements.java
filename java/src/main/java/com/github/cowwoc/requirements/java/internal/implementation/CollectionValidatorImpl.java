/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.CollectionValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @param <E> the type of elements in the collection
 * @param <T> the type of the collection
 */
public final class CollectionValidatorImpl<E, T extends Collection<E>>
	extends AbstractCollectionValidator<CollectionValidator<E, T>, E, T>
	implements CollectionValidator<E, T>
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
	public CollectionValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Pluralizer pluralizer, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, pluralizer, context, failures);
	}
}