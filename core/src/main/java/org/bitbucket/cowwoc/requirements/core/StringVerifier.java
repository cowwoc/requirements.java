/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ext.StringBasedExtension;

/**
 * Verifies a {@link String}.
 *
 * @author Gili Tzabari
 */
public interface StringVerifier
	extends StringBasedExtension<StringVerifier, String>
{
	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return this
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
	 * Ensures that the actual value contains a valid Internet address format.
	 *
	 * @return a verifier for Internet addresses
	 * @throws IllegalArgumentException if the actual value does not contain a valid Internet address
	 *                                  format
	 */
	InetAddressVerifier asInetAddress();

	/**
	 * Ensures that the actual value contains a valid Internet address format.
	 *
	 * @param consumer verifies Internet addresses
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid Internet address
	 *                                  format
	 */
	StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer);

	/**
	 * Returns a verifier for the URI representation of the value.
	 * <p>
	 * Technically-speaking, there is no such thing as an invalid URI format. Per
	 * <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC3986</a>, any String can be
	 * represented as a relative URI, but Java's implementation is based on an
	 * <a href="https://tools.ietf.org/html/rfc2396">older specification</a> where this was not the
	 * case.
	 *
	 * @return a verifier for the URI representation of the value
	 * @throws IllegalArgumentException if the actual value cannot be converted to a Java URI
	 * @see <a href="http://stackoverflow.com/a/27644491/14731">Discussion of Java URI vs RFC3986</a>
	 */
	UriVerifier asUri();

	/**
	 * Ensures that the actual value contains a valid URI format.
	 *
	 * @param consumer verifies URIs
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid URI format
	 */
	StringVerifier asUri(Consumer<UriVerifier> consumer);
}
