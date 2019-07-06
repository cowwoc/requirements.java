/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A PrimitiveNumberValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <T> the type of the integer number
 */
public final class PrimitiveNumberValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractPrimitiveNumberValidatorNoOp<PrimitiveNumberValidator<T>, T>
	implements PrimitiveNumberValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PrimitiveNumberValidatorNoOp(ApplicationScope scope, Configuration config,
	                                    List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected PrimitiveNumberValidator<T> getThis()
	{
		return this;
	}
}
