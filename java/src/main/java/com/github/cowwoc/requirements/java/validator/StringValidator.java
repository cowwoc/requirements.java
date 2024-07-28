/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.validator;

import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.validator.component.ObjectComponent;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.function.Function;

/**
 * Validates the state of a {@code String}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface StringValidator extends
	ValidatorComponent<StringValidator, String>,
	ObjectComponent<StringValidator, String>
{
	/**
	 * Ensures that the value starts with some prefix.
	 *
	 * @param prefix the value that the string must start with
	 * @return this
	 * @throws NullPointerException     if the value or {@code prefix} are null
	 * @throws IllegalArgumentException if the value does not start with {@code prefix}
	 */
	StringValidator startsWith(String prefix);

	/**
	 * Ensures that the value does not start with some prefix.
	 *
	 * @param prefix the value that the string may not start with
	 * @return this
	 * @throws NullPointerException     if the value or {@code prefix} are null
	 * @throws IllegalArgumentException if the value starts with {@code prefix}
	 */
	StringValidator doesNotStartWith(String prefix);

	/**
	 * Ensures that the value ends with some suffix.
	 *
	 * @param suffix the value that the string must end with
	 * @return this
	 * @throws NullPointerException     if the value or {@code suffix} are null
	 * @throws IllegalArgumentException if the value does not end with {@code suffix}
	 */
	StringValidator endsWith(String suffix);

	/**
	 * Ensures that the value does not end with some suffix.
	 *
	 * @param suffix the value that the string may not end with
	 * @return this
	 * @throws NullPointerException     if the value or {@code suffix} are null
	 * @throws IllegalArgumentException if the value ends with {@code suffix}
	 */
	StringValidator doesNotEndWith(String suffix);

	/**
	 * Ensures that the value contains some substring.
	 *
	 * @param expected the string that the value must contain
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the value does not contain {@code expected}
	 */
	StringValidator contains(String expected);

	/**
	 * Ensures that the value does not contain some substring.
	 *
	 * @param unwanted the string that the value may not contain
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the value contains {@code unwanted}
	 */
	StringValidator doesNotContain(String unwanted);

	/**
	 * Ensures that the value matches a regular expression.
	 *
	 * @param regex the regular expression
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value does not match {@code regex}
	 */
	StringValidator matches(String regex);

	/**
	 * Ensures that the value is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not empty
	 * @see #trim()
	 */
	StringValidator isEmpty();

	/**
	 * Ensures that the value is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is empty
	 * @see #trim()
	 */
	StringValidator isNotEmpty();

	/**
	 * Ensures that the value is empty or contains only {@link Character#isWhitespace(int) whitespace}
	 * codepoints.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not empty or contains non-whitespace codepoints
	 */
	StringValidator isBlank();

	/**
	 * Ensures that the value is not empty or contains non-{@link Character#isWhitespace(int) whitespace}
	 * codepoints.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is empty or contains only whitespace codepoints
	 */
	StringValidator isNotBlank();

	/**
	 * Returns a validator for the length of the String.
	 *
	 * @return a validator for the length of the String
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator length();

	/**
	 * Removes all the whitespace from the beginning and end of the value, where whitespace is defined by
	 * {@link String#trim()}.
	 *
	 * @return this
	 * @throws NullPointerException if the value is null
	 */
	StringValidator trim();

	/**
	 * Ensures that the value does not contain leading or trailing whitespace, where whitespace is defined by
	 * {@link String#trim()}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value contains leading or trailing whitespace
	 * @see String#trim()
	 * @see #isEmpty()
	 */
	StringValidator isTrimmed();

	/**
	 * Removes all the whitespace from the beginning and end of the value, where whitespace is defined by
	 * {@link String#strip()}.
	 *
	 * @return this
	 * @throws NullPointerException if the value is null
	 */
	StringValidator strip();

	/**
	 * Ensures that the value does not contain leading or trailing whitespace, where whitespace is defined by
	 * {@link String#strip()}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value contains leading or trailing whitespace
	 * @see #isBlank()
	 */
	StringValidator isStripped();

	/**
	 * Validates the {@code byte} representation of the value.
	 *
	 * @return a validator for the {@code byte} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code byte}
	 * @see Byte#parseByte(String)
	 */
	PrimitiveByteValidator asPrimitiveByte();

	/**
	 * Validates the {@code short} representation of the value.
	 *
	 * @return a validator for the {@code short} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code short}
	 * @see Short#parseShort(String)
	 */
	PrimitiveShortValidator asPrimitiveShort();

	/**
	 * Validates the {@code int} representation of the value.
	 *
	 * @return a validator for the {@code int} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to an {@code int}
	 * @see Integer#parseInt(String)
	 */
	PrimitiveIntegerValidator asPrimitiveInteger();

	/**
	 * Validates the {@code long} representation of the value.
	 *
	 * @return a validator for the {@code long} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code long}
	 * @see Long#parseLong(String)
	 */
	PrimitiveLongValidator asPrimitiveLong();

	/**
	 * Validates the {@code float} representation of the value.
	 *
	 * @return a validator for the {@code float} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code float}
	 * @see Float#parseFloat(String)
	 */
	PrimitiveFloatValidator asPrimitiveFloat();

	/**
	 * Validates the {@code double} representation of the value.
	 *
	 * @return a validator for the {@code double} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code double}
	 * @see Double#parseDouble(String)
	 */
	PrimitiveDoubleValidator asPrimitiveDouble();

	/**
	 * Validates the {@code boolean} representation of the value.
	 *
	 * @return a validator for the {@code boolean} representation of the value
	 * @throws NullPointerException if the value is null
	 * @see Boolean#parseBoolean(String)
	 */
	PrimitiveBooleanValidator asPrimitiveBoolean();

	/**
	 * Validates the {@code char} representation of the value.
	 *
	 * @return a validator for the {@code char} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code char}
	 * @see Character#charValue()
	 */
	PrimitiveCharacterValidator asPrimitiveCharacter();

	/**
	 * Validates the {@code BigInteger} representation of the value.
	 *
	 * @return a validator for the {@code BigInteger} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code BigInteger}
	 * @see BigInteger#BigInteger(String)
	 */
	BigIntegerValidator asBigInteger();

	/**
	 * Validates the {@code BigDecimal} representation of the value.
	 *
	 * @return a validator for the {@code BigDecimal} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code BigDecimal}
	 * @see BigDecimal#BigDecimal(String)
	 */
	BigDecimalValidator asBigDecimal();

	/**
	 * Validates the {@code Path} representation of the value.
	 *
	 * @return a validator for the {@code Path} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code Path}
	 */
	PathValidator asPath();

	/**
	 * Validates the {@code URI} representation of the value.
	 * <p>
	 * Technically-speaking, there is no such thing as an invalid URI format. Per
	 * <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC3986</a>, any String can be
	 * represented as a relative URI, but Java's implementation is based on an
	 * <a href="https://tools.ietf.org/html/rfc2396">older specification</a> where this was not the
	 * case.
	 *
	 * @return a validator for the {@code URI} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code URI}
	 * @see <a href="http://stackoverflow.com/a/27644491/14731">Discussion of Java URI vs RFC3986</a>
	 */
	UriValidator asUri();

	/**
	 * Validates the {@code URL} representation of the value.
	 *
	 * @return a validator for the {@code URL} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to a {@code URL}
	 */
	UrlValidator asUrl();

	/**
	 * Validates the {@code InetAddress} representation of the value.
	 *
	 * @return a validator for the {@code InetAddress} representation of the value
	 * @throws IllegalArgumentException if the value cannot be converted to an {@code InetAddress}
	 * @see InetAddress#getHostAddress()
	 */
	InetAddressValidator asInetAddress();
}