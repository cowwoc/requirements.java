/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

/**
 * Verifies requirements of an {@link Object} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectRequirementsSpi<S extends ObjectRequirementsSpi<S, T>, T>
{
	/**
	 * Overrides the type of exception that will get thrown if a requirement fails.
	 *
	 * @param exception the type of exception to throw, null to disable the override
	 * @return this
	 */
	S withException(Class<? extends RuntimeException> exception);

	/**
	 * Ensures that the parameter is equal to a value.
	 * <p>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 */
	S isEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is equal to a variable.
	 * <p>
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is not equal to the variable; if
	 *                                  {@code name} is empty
	 */
	S isEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a value.
	 * <p>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is equal to value
	 */
	S isNotEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a variable.
	 * <p>
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is equal to the variable; if {@code name}
	 *                                  is empty
	 */
	S isNotEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is an instance of a class.
	 * <p>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	S isInstanceOf(Class<?> type) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is null.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not null
	 */
	S isNull() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not null.
	 * <p>
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	S isNotNull() throws NullPointerException;
}
