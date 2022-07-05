/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.IntegerValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractIntegerValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code IntegerValidator<Long>}.
 */
public final class LongValidatorImpl
	extends AbstractIntegerValidator<IntegerValidator<Long>, Long>
	implements IntegerValidator<Long>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public LongValidatorImpl(ApplicationScope scope, Configuration config, String name, Long actual,
	                         List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected IntegerValidator<Long> getThis()
	{
		return this;
	}

	@Override
	protected IntegerValidator<Long> getNoOp()
	{
		return new IntegerValidatorNoOp<>(getFailures());
	}
}
