/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link String}.
 * <p>
 * All methods (except for {@link #asString()} and those found in {@link ExtensibleObjectValidator}) imply
 * {@link #isNotNull()}.
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
	 * Ensures that the actual value is empty or contains only {@link Character#isWhitespace(int) white space}
	 * codepoints.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not empty or contains non-white space codepoints
	 */
	StringVerifier isBlank();

	/**
	 * Ensures that the actual value is not empty or contains non-{@link Character#isWhitespace(int) white space}
	 * codepoints.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is empty or contains only
	 *                                  {@link Character#isWhitespace(int) white space} codepoints
	 */
	StringVerifier isNotBlank();

	/**
	 * Returns a verifier for the length of the actual value.
	 *
	 * @return a verifier for the length of the actual value
	 */
	SizeVerifier length();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the length of the actual value
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	StringVerifier length(Consumer<SizeVerifier> consumer);

	/**
	 * Removes all white space from the beginning and end of the actual value, where white space is defined as
	 * any character whose codepoint is less than or equal to {@code 'U+0020'} (the space character).
	 *
	 * @return the updated verifier
	 */
	StringVerifier trim();

	/**
	 * Ensures that the actual value does not contain leading or trailing white space, where white space is
	 * defined by {@link #trim()}.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not trimmed
	 * @see #trim()
	 * @see #isBlank()
	 */
	StringVerifier isTrimmed();

	/**
	 * Strips {@linkplain Character#isWhitespace(int) white space} from the beginning and the end of the actual
	 * value, where white space is defined by the Unicode standard.
	 *
	 * @return the updated verifier
	 */
	StringVerifier strip();

	/**
	 * Ensures that the actual value does not contain leading or trailing white space, where white space is
	 * defined by {@link #strip()}.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not trimmed
	 * @see #strip()
	 * @see #isBlank()
	 */
	StringVerifier isStripped();

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
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
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
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
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
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
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

	/**
	 * Returns a verifier for the Boolean representation of the value.
	 *
	 * @return a verifier for the Boolean representation of the value
	 * @see Boolean#parseBoolean(String)
	 * @throws IllegalArgumentException if the actual value cannot be converted to a Boolean
	 */
	BooleanVerifier asBoolean();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asBoolean()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer verifies Booleans
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	StringVerifier asBoolean(Consumer<BooleanVerifier> consumer);

	/**
	 * Returns a verifier for the Short representation of the value.
	 *
	 * @return a verifier for the Short representation of the value
	 * @see Short#parseShort(String)
	 * @throws IllegalArgumentException if the actual value cannot be converted to a Short
	 */
	IntegerVerifier<Short> asShort();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asShort()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates Shorts
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	StringVerifier asShort(Consumer<IntegerVerifier<Short>> consumer);

	/**
	 * Returns a verifier for the Integer representation of the value.
	 *
	 * @return a verifier for the Integer representation of the value
	 * @see Integer#parseInt(String)
	 * @throws IllegalArgumentException if the actual value cannot be converted to an Integer
	 */
	IntegerVerifier<Integer> asInteger();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asInteger()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates Integers
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	StringVerifier asInteger(Consumer<IntegerVerifier<Integer>> consumer);

	/**
	 * Returns a verifier for the Long representation of the value.
	 *
	 * @return a verifier for the Long representation of the value
	 * @see Long#parseLong(String)
	 */
	IntegerVerifier<Long> asLong();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asLong()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates Longs
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value cannot be converted to a Long
	 * @throws NullPointerException if {@code consumer} is null
	 */
	StringVerifier asLong(Consumer<IntegerVerifier<Long>> consumer);

	/**
	 * Returns a verifier for the BigDecimal representation of the value.
	 *
	 * @return a verifier for the BigDecimal representation of the value
	 * @see BigDecimal#BigDecimal(String)
	 */
	BigDecimalVerifier asBigDecimal();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 * <p>
	 * See {@link #asBigDecimal()} for exceptions that may be thrown to the consumer.
	 *
	 * @param consumer validates BigDecimals
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value cannot be converted to a BigDecimal
	 * @throws NullPointerException if {@code consumer} is null
	 */
	StringVerifier asBigDecimal(Consumer<BigDecimalVerifier> consumer);

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code actual} is already a String
	 */
	@Deprecated
	@Override
	StringVerifier asString();
}