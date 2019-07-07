/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractDoubleValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveFloatingPointValidator} for {@code double}s.
 */
public final class PrimitiveDoubleValidatorImpl
	extends AbstractDoubleValidator<PrimitiveFloatingPointValidator<Double>>
	implements PrimitiveFloatingPointValidator<Double>
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
	public PrimitiveDoubleValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                    Double actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected PrimitiveFloatingPointValidator<Double> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveFloatingPointValidator<Double> getNoOp()
	{
		return new PrimitiveFloatingPointValidatorNoOp<>(failures);
	}
}
