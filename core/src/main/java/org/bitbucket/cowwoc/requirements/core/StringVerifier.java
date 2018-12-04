/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

import java.util.function.Consumer;

/**
 * Verifies a {@link String}.
 */
public interface StringVerifier extends ObjectCapabilities<StringVerifier, String>
{
	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value that the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not start with {@code prefix}
	 */
	StringVerifier startsWith(String prefix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value that the string may not start with
	 * @return this
	 * @throws IllegalArgumentException if the actual value starts with {@code prefix}
	 */
	StringVerifier doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value that the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not end with {@code suffix}
	 */
	StringVerifier endsWith(String suffix);

	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value that the string may not end with
	 * @return this
	 * @throws IllegalArgumentException if the actual value ends with {@code suffix}
	 */
	StringVerifier doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value contains a value.
	 *
	 * @param expected the value that the string must contain
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain {@code expected}
	 */
	StringVerifier contains(String expected);

	/**
	 * Ensures that the actual value does not contain a value.
	 *
	 * @param value the value that the string may not contain
	 * @return this
	 * @throws IllegalArgumentException if the actual value contains {@code value}
	 */
	StringVerifier doesNotContain(String value);

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
	 * @return a verifier for the length of the actual value
	 */
	PrimitiveNumberVerifier<Integer> length();

	/**
	 * @param consumer verifies the length of the actual value
	 * @return this
	 */
	StringVerifier length(Consumer<PrimitiveNumberVerifier<Integer>> consumer);

	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return a verifier for the trimmed String representation
	 */
	StringVerifier trim();

	/**
	 * Ensures that the actual value does not contain leading or trailing whitespace.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not trimmed
	 * @see #trim()
	 */
	StringVerifier isTrimmed();

	/**
	 * Ensures that the actual value contains a valid Internet address format.
	 *
	 * @return a verifier for the Internet address representation of the value
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
	 * @throws IllegalArgumentException if the actual value cannot be converted to a URI
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

	/**
	 * @return a verifier for the URL representation of the value
	 * @throws IllegalArgumentException if the actual value cannot be converted to a URL
	 */
	UrlVerifier asUrl();

	/**
	 * Ensures that the actual value contains a valid URL format.
	 *
	 * @param consumer verifies URLs
	 * @return this
	 * @throws IllegalArgumentException if the actual value does not contain a valid URL format
	 */
	StringVerifier asUrl(Consumer<UrlVerifier> consumer);
}
