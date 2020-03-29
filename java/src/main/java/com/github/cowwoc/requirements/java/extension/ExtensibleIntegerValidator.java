/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.IntegerValidator;

/**
 * Validates the requirements of an integer value but the implementing validator is not guaranteed to be a
 * {@link IntegerValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public interface ExtensibleIntegerValidator<S, T extends Number & Comparable<? super T>>
	extends ExtensibleNumberValidator<S, T>
{

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always a whole number
	 */
	@Override
	@Deprecated
	S isWholeNumber();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always a whole number
	 */
	@Override
	@Deprecated
	S isNotWholeNumber();
}
