/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.OptionalValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;
import java.util.Optional;

/**
 * An {@code OptionalValidator} that does nothing.
 */
public final class OptionalValidatorNoOp
	extends AbstractObjectValidatorNoOp<OptionalValidator, Optional<?>>
	implements OptionalValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public OptionalValidatorNoOp(ApplicationScope scope, Configuration config, List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected OptionalValidator getThis()
	{
		return this;
	}

	@Override
	public OptionalValidator isPresent()
	{
		return this;
	}

	@Override
	public OptionalValidator isEmpty()
	{
		return this;
	}

	@Override
	public OptionalValidator contains(Object value)
	{
		return this;
	}

	@Override
	public OptionalValidator contains(Object value, String name)
	{
		return this;
	}
}
