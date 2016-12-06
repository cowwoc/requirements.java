/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
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
	 *
	 * @param suffix the value the string must not end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter ends with suffix
	 */
	StringRequirements doesNotEndWith(String suffix);

	/**
	 * Ensures that the parameter does not start with a value.
	 *
	 * @param prefix the value the string must not start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter starts with prefix
	 */
	StringRequirements doesNotStartWith(String prefix);

	/**
	 * Ensures that the parameter ends with a value.
	 *
	 * @param suffix the value the string must end with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not end with suffix
	 */
	StringRequirements endsWith(String suffix);

	/**
	 * Ensures that the parameter is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not empty
	 * @see #trim()
	 */
	StringRequirements isEmpty();

	/**
	 * Ensures that the parameter is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the parameter is empty
	 * @see #trim()
	 */
	StringRequirements isNotEmpty();

	/**
	 * Ensures that the parameter contains a valid email format.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid email format
	 */
	StringRequirements isEmailFormat();

	/**
	 * Ensures that the parameter contains a valid IP address format.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not contain a valid IP address format
	 */
	StringRequirements isIpAddressFormat();

	/**
	 * Ensures that the parameter starts with a value.
	 *
	 * @param prefix the value the string must start with
	 * @return this
	 * @throws IllegalArgumentException if the parameter does not start with prefix
	 */
	StringRequirements startsWith(String prefix);

	/**
	 * Trims whitespace at the beginning and end of the parameter.
	 *
	 * @return this
	 */
	StringRequirements trim();

	/**
	 * @return requirements over the String length
	 */
	ContainerSizeRequirements length();
}
