/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

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
}