/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleComparableVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of a {@code Comparable} value.
 */
public interface PrimitiveCharacterVerifier
	extends ExtensiblePrimitiveVerifier<PrimitiveCharacterVerifier, Character>,
	ExtensibleComparableVerifier<PrimitiveCharacterVerifier, Character>
{
}