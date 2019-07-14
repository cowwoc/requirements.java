/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.FloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code FloatingPointValidator<Float>}.
 */
public final class FloatValidatorImpl
	extends AbstractFloatValidator<FloatingPointValidator<Float>>
	implements FloatingPointValidator<Float>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public FloatValidatorImpl(ApplicationScope scope, Configuration config, String name, Float actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected FloatingPointValidator<Float> getThis()
	{
		return this;
	}

	@Override
	protected FloatingPointValidator<Float> getNoOp()
	{
		return new FloatingPointValidatorNoOp<>(getFailures());
	}
}
