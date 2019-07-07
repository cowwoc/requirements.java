/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of a {@code Comparable} value.
 */
public interface PrimitiveCharacterVerifier
	extends ExtensiblePrimitiveVerifier<PrimitiveCharacterVerifier, Character>,
	ExtensibleComparableVerifier<PrimitiveCharacterVerifier, Character>
{
}