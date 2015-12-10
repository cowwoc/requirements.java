/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.time.Year;

/**
 * Interface needed for Preconditions.assertThat().
 * <p>
 * @author Gili Tzabari
 */
public interface YearPreconditions extends ObjectPreconditions<YearPreconditions, Year>
{
	/**
	 * Ensures that the parameter is greater than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is less than or equal to {@code value}
	 */
	YearPreconditions isGreaterThan(Year value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to {@code value}
	 */
	YearPreconditions isGreaterThan(Year value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than or bigger than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is less than to {@code value}
	 */
	YearPreconditions isGreaterThanOrEqualTo(Year value, String name)
		throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than or bigger than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	YearPreconditions isGreaterThanOrEqualTo(Year value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	YearPreconditions isIn(Range<Year> range) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than the value of a variable.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is greater than or equal to {@code value}
	 */
	YearPreconditions isLessThan(Year value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than a constant.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	YearPreconditions isLessThan(Year value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than or equal to a variable.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is greater than {@code value}
	 */
	YearPreconditions isLessThanOrEqualTo(Year value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than or equal to a constant.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	YearPreconditions isLessThanOrEqualTo(Year value) throws IllegalArgumentException;
}
