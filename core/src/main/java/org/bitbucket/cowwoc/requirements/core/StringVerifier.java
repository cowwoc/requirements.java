/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.ext.StringBasedExtension;

/**
 * Verifies a {@link String} parameter.
 *
 * @author Gili Tzabari
 */
public interface StringVerifier
	extends StringBasedExtension<StringVerifier, String>, Verifier<StringVerifier>
{
	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return a verifier with the actual value trimmed
	 */
	StringVerifier trim();

	/**
	 * {@inheritDoc}
	 *
	 * @see #trim()
	 */
	@Override
	StringVerifier isEmpty();

	/**
	 * {@inheritDoc}
	 *
	 * @see #trim()
	 */
	@Override
	StringVerifier isNotEmpty();

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
