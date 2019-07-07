/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

/**
 * Validates the requirements of a {@code Comparable} value.
 */
public interface PrimitiveCharacterValidator
	extends ExtensiblePrimitiveValidator<PrimitiveCharacterValidator, Character>,
	ExtensibleComparableValidator<PrimitiveCharacterValidator, Character>
{
}