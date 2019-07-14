/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveNumberValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected PrimitiveNumberValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveNumberValidator<T> getNoOp()
	{
		return new PrimitiveNumberValidatorNoOp<>(getFailures());
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
		ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
			name + " can never be null").
			addContext("Actual", actual);
		addFailure(failure);
		return this;
	}
}
