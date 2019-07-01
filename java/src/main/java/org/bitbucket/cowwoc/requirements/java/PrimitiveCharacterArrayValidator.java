/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayValidator;

/**
 * Validates the requirements of a char array.
 */
public interface PrimitiveCharacterArrayValidator
	extends ExtensibleArrayValidator<PrimitiveCharacterArrayValidator, Character, char[]>
{
}