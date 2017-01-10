/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.ext;

import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;

/**
 * Verifier for String-based types that aren't actually strings (e.g. email address).
 *
 * @param <S> the type of the non-extension interface extending this interface
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public interface StringBasedExtension<S extends StringBasedExtension<S, T>, T extends String>
	extends ObjectVerifierExtension<S, T>
{
	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value that the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not start with {@code prefix}
	 */
	S startsWith(String prefix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value that the string may not start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value starts with {@code prefix}
	 */
	S doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value that the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not end with {@code suffix}
	 */
	S endsWith(String suffix);

	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value that the string may not end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value ends with {@code suffix}
	 */
	S doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	S isNotEmpty();

	/**
	 * @return a verifier for the length of the actual value
	 */
	ContainerSizeVerifier length();

	/**
	 * @param consumer verifies the length of the actual value
	 * @return this
	 */
	StringBasedExtension<S, T> length(Consumer<ContainerSizeVerifier> consumer);
}
