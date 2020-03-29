/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.extension.AbstractFloatValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveFloatingPointValidator} for {@code float}s.
 */
public final class PrimitiveFloatValidatorImpl
	extends AbstractFloatValidator<PrimitiveFloatingPointValidator<Float>>
	implements PrimitiveFloatingPointValidator<Float>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveFloatValidatorImpl(ApplicationScope scope, Configuration config, String name, Float actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected PrimitiveFloatingPointValidator<Float> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveFloatingPointValidator<Float> getNoOp()
	{
		return new PrimitiveFloatingPointValidatorNoOp<>(getFailures());
	}
}
