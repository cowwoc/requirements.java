/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ListValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.diff.ContextGenerator;
import com.github.cowwoc.requirements.java.internal.diff.ContextLine;
import com.github.cowwoc.requirements.java.internal.extension.AbstractCollectionValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.List;

/**
 * Default implementation of {@code ListValidator}.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public final class ListValidatorImpl<L extends List<E>, E>
	extends AbstractCollectionValidator<ListValidator<L, E>, L, E>
	implements ListValidator<L, E>
{
	/**
	 * Creates a ListValidatorImpl with no validation failures.
	 *
	 * @param scope      the application configuration
	 * @param config     the instance configuration
	 * @param name       the name of the value
	 * @param actual     the actual value
	 * @param pluralizer returns the singular or plural form of an element type
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code pluralizer} are null.
	 *                        If {@code name} is empty.
	 */
	public ListValidatorImpl(ApplicationScope scope, Configuration config, String name, L actual,
	                         Pluralizer pluralizer)
	{
		this(scope, config, name, actual, pluralizer, NO_FAILURES);
	}

	/**
	 * Creates a ListValidatorImpl with existing validation failures.
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
	public ListValidatorImpl(ApplicationScope scope, Configuration config, String name, L actual,
	                         Pluralizer pluralizer, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, pluralizer, failures);
	}

	@Override
	protected ListValidator<L, E> getThis()
	{
		return this;
	}

	@Override
	protected ListValidator<L, E> getNoOp()
	{
		return new ListValidatorNoOp<>(getFailures());
	}

	@Override
	protected List<ContextLine> getContext(Object expected, boolean expectedInMessage)
	{
		ContextGenerator contextGenerator = new ContextGenerator(config, scope);
		@SuppressWarnings("unchecked")
		L expectedAsList = (L) expected;
		return contextGenerator.getContext("Actual", actual, "Expected",
			expectedAsList, expectedInMessage);
	}
}