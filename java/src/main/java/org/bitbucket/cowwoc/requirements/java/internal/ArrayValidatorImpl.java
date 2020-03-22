/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Collection;
import java.util.List;

/**
 * Default implementation of {@code ArrayValidator}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayValidatorImpl<A, E> extends AbstractArrayValidator<ArrayValidator<A, E>, A, E>
	implements ArrayValidator<A, E>
{
	/**
	 * Creates a new ArrayValidatorImpl with no validation failures.
	 *
	 * @param scope              the application configuration
	 * @param config             the instance configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, A actual,
	                          Collection<E> actualAsCollection)
	{
		this(scope, config, name, actual, actualAsCollection, NO_FAILURES);
	}

	/**
	 * @param scope              the application configuration
	 * @param config             the instance configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @param failures           the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, A actual,
	                          Collection<E> actualAsCollection, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, actualAsCollection, failures);
	}

	@Override
	protected ArrayValidator<A, E> getThis()
	{
		return this;
	}

	@Override
	protected ArrayValidator<A, E> getNoOp()
	{
		return new ArrayValidatorNoOp<>(getFailures());
	}
}