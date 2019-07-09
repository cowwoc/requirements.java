/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of a {@link Comparable}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <T> the type of the value being validated
 */
public interface ComparableValidator<T extends Comparable<? super T>>
	extends ExtensibleComparableValidator<ComparableValidator<T>, T>
{
}