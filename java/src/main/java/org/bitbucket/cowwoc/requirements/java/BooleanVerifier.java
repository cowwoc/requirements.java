/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of a {@code boolean} value.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface BooleanVerifier extends ExtensibleBooleanVerifier<BooleanVerifier>
{
}
