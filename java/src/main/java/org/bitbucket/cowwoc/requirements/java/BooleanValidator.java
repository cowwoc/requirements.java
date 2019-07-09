/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleBooleanValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of a {@code boolean} value.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 */
public interface BooleanValidator extends ExtensibleBooleanValidator<BooleanValidator>
{
}
