/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractBooleanValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveBooleanValidator}.
 */
public final class PrimitiveBooleanValidatorImpl
	extends AbstractBooleanValidator<PrimitiveBooleanValidator>
	implements PrimitiveBooleanValidator
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
	public PrimitiveBooleanValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                     boolean actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected PrimitiveBooleanValidator getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveBooleanValidator getNoOp()
	{
		return new PrimitiveBooleanValidatorNoOp(getFailures());
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}
}
