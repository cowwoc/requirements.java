/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.BooleanValidator;

/**
 * Validates the requirements of a boolean value but the implementing validator is not guaranteed to be a
 * {@link BooleanValidator}.
 *
 * @param <S> the type of validator returned by the methods
 */
public interface ExtensibleBooleanValidator<S> extends ExtensibleComparableValidator<S, Boolean>
{
	/**
	 * Ensures that the actual value is {@code true}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not true
	 */
	S isTrue();

	/**
	 * Ensures that the actual value is {@code false}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not false
	 */
	S isFalse();
}