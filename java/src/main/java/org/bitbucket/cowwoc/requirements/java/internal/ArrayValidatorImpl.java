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

import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of {@code ArrayValidator}.
 *
 * @param <E> the type of elements in the array
 */
public final class ArrayValidatorImpl<E> extends AbstractArrayValidator<ArrayValidator<E>, E, E[]>
	implements ArrayValidator<E>
{
	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @return null if the array is null; otherwise, a view of the array as a collection
	 */
	private static <E> List<E> asCollection(E[] array)
	{
		if (array == null)
			return null;
		return Arrays.asList(array);
	}

	/**
	 * Creates a new ArrayValidatorImpl with no validation failures.
	 *
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, E[] actual)
	{
		this(scope, config, name, actual, NO_FAILURES);
	}

	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, E[] actual,
	                   List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, asCollection(actual), failures);
	}

	@Override
	protected ArrayValidator<E> getThis()
	{
		return this;
	}

	@Override
	protected ArrayValidator<E> getNoOp()
	{
		return new ArrayValidatorNoOp<>(getFailures());
	}
}