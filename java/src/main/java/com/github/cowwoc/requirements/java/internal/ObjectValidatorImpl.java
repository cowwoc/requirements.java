/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ObjectValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code ObjectValidator}.
 *
 * @param <T> the type of the value being validated
 */
public final class ObjectValidatorImpl<T> extends AbstractObjectValidator<ObjectValidator<T>, T>
	implements ObjectValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public ObjectValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual,
	                           List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected ObjectValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected ObjectValidator<T> getNoOp()
	{
		return new ObjectValidatorNoOp<>(getFailures());
	}
}