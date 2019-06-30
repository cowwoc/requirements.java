/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.FloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code FloatingPointValidator<Double>}.
 */
public final class DoubleValidatorImpl
	extends ExtensibleDoubleValidatorImpl<FloatingPointValidator<Double>>
	implements FloatingPointValidator<Double>
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public DoubleValidatorImpl(ApplicationScope scope, String name, Double actual, Configuration config,
	                           List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	protected FloatingPointValidator<Double> getThis()
	{
		return this;
	}
}
