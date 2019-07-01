/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveBooleanValidator}.
 */
public class PrimitiveBooleanValidatorImpl
	extends AbstractBooleanValidator<PrimitiveBooleanValidator>
	implements PrimitiveBooleanValidator
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public PrimitiveBooleanValidatorImpl(ApplicationScope scope, String name, boolean actual,
	                                     Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}
}