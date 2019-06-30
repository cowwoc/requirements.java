/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleComparableValidator;

/**
 * Validates the requirements of a {@link Comparable}.
 *
 * @param <T> the type of the value
 */
public interface ComparableValidator<T extends Comparable<? super T>>
	extends ExtensibleComparableValidator<ComparableValidator<T>, T>
{
}