/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.function.Consumer;

/**
 * Validates the requirements of a {@link String}.
 * <p>
 * All methods (except for {@link #asString()} and those found in {@link ExtensibleObjectValidator}) imply
 * {@link #isNotNull()}.
 */
public interface StringValidator extends ExtensibleObjectValidator<StringValidator, String>
{
	/**
	 * Ensures that the actual value starts with a value.
	 *
	 * @param prefix the value that the string must start with
	 * @return the updated validator
	 * @throws NullPointerException if {@code prefix} is null
	 */
	StringValidator startsWith(String prefix);

	/**
	 * Ensures that the actual value does not start with a value.
	 *
	 * @param prefix the value that the string may not start with
	 * @return the updated validator
	 * @throws NullPointerException if {@code prefix} is null
	 */
	StringValidator doesNotStartWith(String prefix);

	/**
	 * Ensures that the actual value ends with a value.
	 *
	 * @param suffix the value that the string must end with
	 * @return the updated validator
	 * @throws NullPointerException if {@code suffix} is null
	 */
	StringValidator endsWith(String suffix);

	/**
	 * Ensures that the actual value does not end with a value.
	 *
	 * @param suffix the value that the string may not end with
	 * @return the updated validator
	 * @throws NullPointerException if {@code suffix} is null
	 */
	StringValidator doesNotEndWith(String suffix);

	/**
	 * Ensures that the actual value contains a value.
	 *
	 * @param expected the value that the string must contain
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	StringValidator contains(String expected);

	/**
	 * Ensures that the actual value does not contain a value.
	 *
	 * @param value the value that the string may not contain
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	StringValidator doesNotContain(String value);

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 * @see #trim()
	 */
	StringValidator isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated validator
	 * @see #trim()
	 */
	StringValidator isNotEmpty();

	/**
	 * Returns a validator for the length of the actual value.
	 *
	 * @return a validator for the length of the actual value
	 */
	SizeValidator length();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the length of the actual value
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringValidator length(Consumer<SizeValidator> consumer);

	/**
	 * Trims whitespace at the beginning and end of the actual value.
	 *
	 * @return the updated validator
	 */
	StringValidator trim();

	/**
	 * Ensures that the actual value does not contain leading or trailing whitespace.
	 *
	 * @return the updated validator
	 * @see #trim()
	 */
	StringValidator isTrimmed();

	/**
	 * Ensures that the actual value contains a valid Internet address format.
	 *
	 * @return a validator for the Internet address representation of the value
	 */
	InetAddressValidator asInetAddress();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asInetAddress()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates Internet addresses
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringValidator asInetAddress(Consumer<InetAddressValidator> consumer);

	/**
	 * Returns a validator for the URI representation of the value.
	 * <p>
	 * Technically-speaking, there is no such thing as an invalid URI format. Per
	 * <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC3986</a>, any String can be
	 * represented as a relative URI, but Java's implementation is based on an
	 * <a href="https://tools.ietf.org/html/rfc2396">older specification</a> where this was not the
	 * case.
	 *
	 * @return a validator for the URI representation of the value
	 * @see <a href="http://stackoverflow.com/a/27644491/14731">Discussion of Java URI vs RFC3986</a>
	 */
	UriValidator asUri();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUri()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates URIs
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringValidator asUri(Consumer<UriValidator> consumer);

	/**
	 * Returns a validator for the URL representation of the value.
	 *
	 * @return a validator for the URL representation of the value
	 */
	UrlValidator asUrl();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asUrl()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates URLs
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringValidator asUrl(Consumer<UrlValidator> consumer);
}