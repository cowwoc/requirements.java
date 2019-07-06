/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A SizeValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class SizeValidatorNoOp
	extends AbstractPrimitiveNumberValidatorNoOp<SizeValidator, Integer>
	implements SizeValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public SizeValidatorNoOp(ApplicationScope scope, Configuration config, List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected SizeValidator getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public SizeValidator isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}

	@Override
	@Deprecated
	public SizeValidator isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}
}
