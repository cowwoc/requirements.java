/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An implementation of {@code ClassValidator} that does nothing.
 *
 * @param <T> the type of the class
 */
public final class ClassValidatorNoOp<T>
	extends AbstractObjectValidatorNoOp<ClassValidator<T>, Class<T>>
	implements ClassValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public ClassValidatorNoOp(ApplicationScope scope, Configuration config, List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected ClassValidator<T> getThis()
	{
		return this;
	}

	@Override
	public ClassValidator<T> isSupertypeOf(Class<?> type)
	{
		return this;
	}
}
