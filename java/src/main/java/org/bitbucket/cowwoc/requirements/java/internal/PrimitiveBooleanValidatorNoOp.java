/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An implementation of {@code PrimitiveBooleanValidator} that does nothing.
 */
public final class PrimitiveBooleanValidatorNoOp
	extends AbstractComparableValidatorNoOp<PrimitiveBooleanValidator, Boolean>
	implements PrimitiveBooleanValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PrimitiveBooleanValidatorNoOp(ApplicationScope scope, Configuration config,
	                                     List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected PrimitiveBooleanValidator getThis()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isTrue()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isFalse()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveBooleanValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveBooleanValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
