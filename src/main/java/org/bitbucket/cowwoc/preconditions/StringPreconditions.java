/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;

/**
 * Interface needed for Preconditions.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface StringPreconditions extends ObjectPreconditions<StringPreconditions, String>
{
	/**
	 * Ensures that the parameter does not end with a value.
	 * <p>
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	StringPreconditions doesNotEndWith(String suffix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter does not start with a value.
	 * <p>
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	StringPreconditions doesNotStartWith(String prefix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter ends with a value.
	 * <p>
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	StringPreconditions endsWith(String suffix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter's length is equal to a single value.
	 * <p>
	 * @param length the expected length of the string
	 * @return this
	 * @throws IllegalArgumentException if parameter length is incorrect
	 */
	StringPreconditions hasLength(int length) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter isn't too long.
	 * <p>
	 * @param maxLength the maximum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too long
	 */
	StringPreconditions hasMaximumLength(int maxLength) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter isn't too short.
	 * <p>
	 * @param minLength the minimum length allowed for the string
	 * @return this
	 * @throws IllegalArgumentException if parameter is too short
	 */
	StringPreconditions hasMinimumLength(int minLength) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains a valid email format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	StringPreconditions isEmailFormat() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not empty
	 * @see #trim()
	 */
	StringPreconditions isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains a valid IP address format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	StringPreconditions isIpAddressFormat() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 * @see #trim()
	 */
	StringPreconditions isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter length is within a range.
	 * <p>
	 * @param range the range of acceptable parameter lengths
	 * @return this
	 * @throws IllegalArgumentException if parameter's length is outside of the specified range
	 * @throws NullPointerException     if range is null
	 */
	StringPreconditions lengthIn(Range<Integer> range) throws NullPointerException,
		IllegalArgumentException;

	/**
	 * Ensures that the parameter starts with a value.
	 * <p>
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not start with prefix
	 */
	StringPreconditions startsWith(String prefix) throws IllegalArgumentException;

	/**
	 * Trims whitespace at the beginning and end of the parameter.
	 * <p>
	 * @return this
	 */
	StringPreconditions trim();
}
