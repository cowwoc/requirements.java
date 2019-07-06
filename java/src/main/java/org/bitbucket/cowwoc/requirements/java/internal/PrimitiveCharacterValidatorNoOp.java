/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A {@code PrimitiveCharacterValidator} that does nothing.
 */
public final class PrimitiveCharacterValidatorNoOp
	extends AbstractComparableValidatorNoOp<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PrimitiveCharacterValidatorNoOp(ApplicationScope scope, Configuration config,
	                                       List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected PrimitiveCharacterValidator getThis()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveCharacterValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveCharacterValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
