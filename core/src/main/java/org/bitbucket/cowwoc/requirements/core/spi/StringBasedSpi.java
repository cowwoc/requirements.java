/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;

/**
 * Verifier for String-based types that aren't actually strings (e.g. email address).
 *
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface StringBasedSpi<S extends StringBasedSpi<S, T>, T extends String>
	extends ObjectVerifierSpi<S, T>
{
	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value ends with suffix
	 */
	S doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value starts with prefix
	 */
	S doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not end with suffix
	 */
	S endsWith(String suffix);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 * @see #trim()
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 * @see #trim()
	 */
	S isNotEmpty();

	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not start with prefix
	 */
	S startsWith(String prefix);

	/**
	 * @return verifier for the length of the actual value
	 */
	ContainerSizeVerifier length();
}
