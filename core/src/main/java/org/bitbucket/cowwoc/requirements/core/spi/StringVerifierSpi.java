/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import org.bitbucket.cowwoc.requirements.core.EmailAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;

/**
 * Verifier for a {@link String}.
 *
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface StringVerifierSpi<S extends StringVerifierSpi<S, T>, T extends String>
	extends StringBasedSpi<S, T>
{
	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return a verifier with the actual value trimmed
	 */
	S trim();

	/**
	 * @return a verifier for email addresses
	 * @throws IllegalArgumentException if the actual value does not contain a valid email format
	 */
	EmailAddressVerifier asEmailAddress();

	/**
	 * Ensures that the actual value contains a valid Internet address format.
	 *
	 * @return a verifier for Internet addresses
	 * @throws IllegalArgumentException if the actual value does not contain a valid Internet address
	 *                                  format
	 */
	InetAddressVerifier asInetAddress();

	/**
	 * Ensures that the actual value contains a valid URI format.
	 *
	 * @return a verifier for URIs
	 * @throws IllegalArgumentException if the actual value does not contain a valid URI format
	 */
	UriVerifier asUri();
}
