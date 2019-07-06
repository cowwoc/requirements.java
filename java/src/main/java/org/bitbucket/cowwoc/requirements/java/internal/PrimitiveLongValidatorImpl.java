/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveIntegerValidator} for {@code long}s.
 */
public final class PrimitiveLongValidatorImpl
	extends AbstractIntegerValidator<PrimitiveIntegerValidator<Long>, Long>
	implements PrimitiveIntegerValidator<Long>
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
	public PrimitiveLongValidatorImpl(ApplicationScope scope, Configuration config, String name, Long actual,
	                                  List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected PrimitiveIntegerValidator<Long> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveIntegerValidator<Long> getNoOp()
	{
		return new PrimitiveIntegerValidatorNoOp<>(scope, config, failures);
	}
}
