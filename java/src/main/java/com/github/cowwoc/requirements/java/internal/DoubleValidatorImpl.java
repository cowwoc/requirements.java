/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.FloatingPointValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractDoubleValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code FloatingPointValidator<Double>}.
 */
public final class DoubleValidatorImpl
	extends AbstractDoubleValidator<FloatingPointValidator<Double>>
	implements FloatingPointValidator<Double>
{
	/**
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public DoubleValidatorImpl(ApplicationScope scope, Configuration config, String name, Double actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected FloatingPointValidator<Double> getThis()
	{
		return this;
	}
}