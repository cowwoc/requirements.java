/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.BooleanVerifier;

/**
 * Verifies the requirements of a boolean value but the implementing verifier is not guaranteed to be a
 * {@link BooleanVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 */
public interface ExtensibleBooleanVerifier<S> extends ExtensibleObjectVerifier<S, Boolean>
{
	/**
	 * Ensures that the actual value is {@code true}.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not true
	 */
	S isTrue();

	/**
	 * Ensures that the actual value is {@code false}.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not false
	 */
	S isFalse();
}
