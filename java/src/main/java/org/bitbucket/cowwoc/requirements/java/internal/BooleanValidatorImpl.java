/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BooleanValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code BooleanValidator}.
 */
public final class BooleanValidatorImpl
	extends AbstractBooleanValidator<BooleanValidator>
	implements BooleanValidator
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public BooleanValidatorImpl(ApplicationScope scope, Configuration config, String name, Boolean actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected BooleanValidator getThis()
	{
		return this;
	}

	@Override
	protected BooleanValidator getNoOp()
	{
		return new BooleanValidatorNoOp(getFailures());
	}
}
