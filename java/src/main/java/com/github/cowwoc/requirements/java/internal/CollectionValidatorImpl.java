/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractCollectionValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code CollectionValidator}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionValidatorImpl<C extends Collection<E>, E>
	extends AbstractCollectionValidator<CollectionValidator<C, E>, C, E>
	implements CollectionValidator<C, E>
{
	/**
	 * Creates a CollectionValidatorImpl with existing validation failures.
	 *
	 * @param scope      the application configuration
	 * @param config     the instance configuration
	 * @param name       the name of the value
	 * @param actual     the actual value
	 * @param pluralizer returns the singular or plural form of an element type
	 * @param failures   the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name}, {@code pluralizer} or
	 *                        {@code failures} are null. If {@code name} is empty.
	 */
	public CollectionValidatorImpl(ApplicationScope scope, Configuration config, String name, C actual,
	                               Pluralizer pluralizer, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, pluralizer, failures);
	}

	@Override
	protected CollectionValidator<C, E> getThis()
	{
		return this;
	}

	@Override
	protected CollectionValidator<C, E> getNoOp()
	{
		return new CollectionValidatorNoOp<>(getFailures());
	}
}