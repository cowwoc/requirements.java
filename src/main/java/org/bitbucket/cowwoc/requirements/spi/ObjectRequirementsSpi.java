/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import com.google.common.annotations.Beta;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.Verifier;

/**
 * Verifies requirements of an {@link Object} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectRequirementsSpi<S extends ObjectRequirementsSpi<S, T>, T> extends Verifier
{
	@Override
	S withException(Class<? extends RuntimeException> exception);

	@Override
	S addContext(String key, Object value) throws NullPointerException;

	@Override
	S withContext(List<Entry<String, Object>> context) throws NullPointerException;

	/**
	 * Ensures that the parameter is equal to a value.
	 *
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is equal to a variable.
	 *
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is not equal to the variable; if
	 *                                  {@code name} is empty
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a value.
	 *
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is equal to value
	 */
	S isNotEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a variable.
	 *
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is equal to the variable; if {@code name}
	 *                                  is empty
	 */
	S isNotEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that a collection contains the parameter.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain {@code parameter}
	 */
	@Beta
	S isIn(Collection<T> collection)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	S isInstanceOf(Class<?> type) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is null.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is not null
	 */
	S isNull() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not null.
	 *
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	S isNotNull() throws NullPointerException;
}
