/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleIntegerValidator;

/**
 * Validates the requirements of an integer number.
 *
 * @param <T> the type of the integer number
 */
public interface IntegerValidator<T extends Number & Comparable<? super T>>
	extends ExtensibleIntegerValidator<IntegerValidator<T>, T>
{
}