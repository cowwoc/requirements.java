/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code ObjectValidator}.
 *
 * @param <T> the type of the value being validated
 */
public final class ObjectValidatorImpl<T> extends AbstractObjectValidator<ObjectValidator<T>, T>
	implements ObjectValidator<T>
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public ObjectValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected ObjectValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected ObjectValidator<T> getNoOp()
	{
		return new ObjectValidatorNoOp<>(getFailures());
	}
}