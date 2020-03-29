/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractDoubleValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveFloatingPointValidator} for {@code double}s.
 */
public final class PrimitiveDoubleValidatorImpl
	extends AbstractDoubleValidator<PrimitiveFloatingPointValidator<Double>>
	implements PrimitiveFloatingPointValidator<Double>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveDoubleValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                    Double actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected PrimitiveFloatingPointValidator<Double> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveFloatingPointValidator<Double> getNoOp()
	{
		return new PrimitiveFloatingPointValidatorNoOp<>(getFailures());
	}
}
