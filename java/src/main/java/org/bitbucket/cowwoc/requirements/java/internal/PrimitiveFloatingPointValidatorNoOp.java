/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A {@code PrimitiveFloatingPointValidator} that does nothing.
 *
 * @param <T> the type of the floating-point number
 */
public final class PrimitiveFloatingPointValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractFloatingPointValidatorNoOp<PrimitiveFloatingPointValidator<T>, T>
	implements PrimitiveFloatingPointValidator<T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PrimitiveFloatingPointValidatorNoOp(ApplicationScope scope, Configuration config,
	                                           List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected PrimitiveFloatingPointValidator<T> getThis()
	{
		return this;
	}
}
