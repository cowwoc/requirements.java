/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

/**
 * Validates the requirements of the size of a collection.
 */
public interface SizeValidator
	extends ExtensiblePrimitiveValidator<SizeValidator, Integer>,
	ExtensibleNumberValidator<SizeValidator, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	SizeValidator isNegative();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	SizeValidator isNotNegative();
}
