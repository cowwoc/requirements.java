/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.NumberValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code NumberValidator}.
 *
 * @param <T> the type of the value being validated
 */
public final class NumberValidatorImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidator<NumberValidator<T>, T>
	implements NumberValidator<T>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public NumberValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected NumberValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected NumberValidator<T> getNoOp()
	{
		return new NumberValidatorNoOp<>(getFailures());
	}
}
