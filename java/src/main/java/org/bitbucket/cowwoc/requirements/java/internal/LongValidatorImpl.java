/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.IntegerValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code IntegerValidator<Long>}.
 */
public final class LongValidatorImpl
	extends AbstractIntegerValidator<IntegerValidator<Long>, Long>
	implements IntegerValidator<Long>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public LongValidatorImpl(ApplicationScope scope, Configuration config, String name, Long actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected IntegerValidator<Long> getThis()
	{
		return this;
	}

	@Override
	protected IntegerValidator<Long> getNoOp()
	{
		return new IntegerValidatorNoOp<>(getFailures());
	}
}
