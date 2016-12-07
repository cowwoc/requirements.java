/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.ObjectVerifierSpi;

/**
 * Interface needed for Requirements.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface StringVerifier
	extends ObjectVerifierSpi<StringVerifier, String>, Isolatable<StringVerifier>
{
	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value ends with suffix
	 */
	StringVerifier doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value starts with prefix
	 */
	StringVerifier doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not end with suffix
	 */
	StringVerifier endsWith(String suffix);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 * @see #trim()
	 */
	StringVerifier isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 * @see #trim()
	 */
	StringVerifier isNotEmpty();

	/**
	 * Ensures that the actual value contains a valid email format.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid email format
	 */
	StringVerifier isEmailFormat();

	/**
	 * Ensures that the actual value contains a valid IP address format.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid IP address format
	 */
	StringVerifier isIpAddressFormat();

	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not start with prefix
	 */
	StringVerifier startsWith(String prefix);

	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return a StringVerifier with the actual value trimmed
	 */
	StringVerifier trim();

	/**
	 * @return verifier for {@code String.length()}
	 */
	ContainerSizeVerifier length();
}
