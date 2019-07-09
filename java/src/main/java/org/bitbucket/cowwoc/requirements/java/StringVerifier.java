/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link String}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface StringVerifier extends ExtensibleObjectVerifier<StringVerifier, String>
{
	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value that the string must start with
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code prefix} is null
	 * @throws IllegalArgumentException if the actual value does not start with {@code prefix}
	 */
	StringVerifier startsWith(String prefix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value that the string may not start with
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code prefix} is null
	 * @throws IllegalArgumentException if the actual value starts with {@code prefix}
	 */
	StringVerifier doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value that the string must end with
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code suffix} is null
	 * @throws IllegalArgumentException if the actual value does not end with {@code suffix}
	 */
	StringVerifier endsWith(String suffix);

	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value that the string may not end with
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code suffix} is null
	 * @throws IllegalArgumentException if the actual value ends with {@code suffix}
	 */
	StringVerifier doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value contains a value.
	 *
	 * @param expected the value that the string must contain
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the actual value does not contain {@code expected}
	 */
	StringVerifier contains(String expected);

	/**
	 * Ensures that the actual value does not contain a value.
	 *
	 * @param value the value that the string may not contain
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value contains {@code value}
	 */
	StringVerifier doesNotContain(String value);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not empty
	 * @see #trim()
	 */
	StringVerifier isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is empty
	 * @see #trim()
	 */
	StringVerifier isNotEmpty();

	/**
	 * Returns a verifier for the length of the actual value.
	 *
	 * @return a verifier for the length of the actual value
	 */
	SizeVerifier length();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the length of the actual value
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringVerifier length(Consumer<SizeVerifier> consumer);

	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return the updated verifier
	 */
	StringVerifier trim();

	/**
	 * Ensures that the actual value does not contain leading or trailing whitespace.
	 *
	 * @return the updated verifier
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
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asInetAddress()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies Internet addresses
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
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
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUri()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies URIs
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringVerifier asUri(Consumer<UriVerifier> consumer);

	/**
	 * Returns a verifier for the URL representation of the value.
	 *
	 * @return a verifier for the URL representation of the value
	 * @throws IllegalArgumentException if the actual value cannot be converted to a URL
	 */
	UrlVerifier asUrl();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUrl()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies URLs
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringVerifier asUrl(Consumer<UrlVerifier> consumer);
}
