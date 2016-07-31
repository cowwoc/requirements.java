/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Interface needed for Requirements.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface StringRequirements
	extends ObjectRequirementsSpi<StringRequirements, String>, Isolatable<StringRequirements>
{
	/**
	 * Ensures that the parameter does not end with a value.
	 * <p>
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	StringRequirements doesNotEndWith(String suffix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter does not start with a value.
	 * <p>
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	StringRequirements doesNotStartWith(String prefix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter ends with a value.
	 * <p>
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	StringRequirements endsWith(String suffix) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not empty
	 * @see #trim()
	 */
	StringRequirements isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 * @see #trim()
	 */
	StringRequirements isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains a valid email format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	StringRequirements isEmailFormat() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains a valid IP address format.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	StringRequirements isIpAddressFormat() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter starts with a value.
	 * <p>
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not start with prefix
	 */
	StringRequirements startsWith(String prefix) throws IllegalArgumentException;

	/**
	 * Trims whitespace at the beginning and end of the parameter.
	 * <p>
	 * @return this
	 */
	StringRequirements trim();

	/**
	 * @return requirements over the String length
	 */
	StringLengthRequirements length();
}
