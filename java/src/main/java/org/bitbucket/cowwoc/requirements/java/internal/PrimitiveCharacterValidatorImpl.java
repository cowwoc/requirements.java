/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code PrimitiveCharacterValidator}.
 */
public class PrimitiveCharacterValidatorImpl
	extends AbstractComparableValidator<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public PrimitiveCharacterValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                       char actual, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected PrimitiveCharacterValidator getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveCharacterValidator getNoOp()
	{
		return new PrimitiveCharacterValidatorNoOp(scope, config, failures);
	}

	@Override
	@Deprecated
	public PrimitiveCharacterValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}

	@Override
	@Deprecated
	public PrimitiveCharacterValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}
}
