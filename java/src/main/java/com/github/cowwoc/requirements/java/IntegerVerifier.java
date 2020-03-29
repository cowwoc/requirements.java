/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;

/**
 * Verifies the requirements of an integer number.
 *
 * @param <T> the type of the integer number
 */
public interface IntegerVerifier<T extends Number & Comparable<? super T>>
	extends ExtensibleIntegerVerifier<IntegerVerifier<T>, T>
{
}
