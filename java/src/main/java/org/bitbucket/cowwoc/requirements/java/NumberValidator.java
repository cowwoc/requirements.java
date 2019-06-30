/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberValidator;

/**
 * Validates the requirements of for a {@link Number}.
 *
 * @param <T> the type of the value
 */
public interface NumberValidator<T extends Number & Comparable<? super T>>
	extends ExtensibleNumberValidator<NumberValidator<T>, T>
{
}
