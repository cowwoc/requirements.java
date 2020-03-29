/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Default implementation of {@code PrimitiveCharacterValidator}.
 */
public final class PrimitiveCharacterValidatorImpl
	extends AbstractComparableValidator<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public PrimitiveCharacterValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                       char actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
	}

	@Override
	protected PrimitiveCharacterValidator getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveCharacterValidator getNoOp()
	{
		return new PrimitiveCharacterValidatorNoOp(getFailures());
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
