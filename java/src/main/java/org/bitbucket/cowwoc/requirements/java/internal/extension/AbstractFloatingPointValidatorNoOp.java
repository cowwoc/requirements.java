/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An {@code ExtensibleFloatingPointValidator} that does nothing.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractFloatingPointValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberValidatorNoOp<S, T>
	implements ExtensibleFloatingPointValidator<S, T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public AbstractFloatingPointValidatorNoOp(ApplicationScope scope, Configuration config,
	                                          List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	public S isNumber()
	{
		return getThis();
	}

	@Override
	public S isNotNumber()
	{
		return getThis();
	}

	@Override
	public S isFinite()
	{
		return getThis();
	}

	@Override
	public S isNotFinite()
	{
		return getThis();
	}
}
