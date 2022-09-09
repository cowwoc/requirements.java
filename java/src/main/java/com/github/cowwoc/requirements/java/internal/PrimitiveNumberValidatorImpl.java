/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.PrimitiveNumberValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveNumberValidator}.
 *
 * @param <T> the type of the value being validated
 */
public final class PrimitiveNumberValidatorImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidator<PrimitiveNumberValidator<T>, T>
	implements PrimitiveNumberValidator<T>
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
	public PrimitiveNumberValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected PrimitiveNumberValidator<T> getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public PrimitiveNumberValidator<T> isNull()
	{
		return neverNull();
	}

	@Override
	@Deprecated
	public PrimitiveNumberValidator<T> isNotNull()
	{
		return neverNull();
	}

	private PrimitiveNumberValidator<T> neverNull()
	{
		if (fatalFailure)
			return this;
		ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be null").
			addContext("Actual", actual);
		addFailure(failure);
		return this;
	}
}
