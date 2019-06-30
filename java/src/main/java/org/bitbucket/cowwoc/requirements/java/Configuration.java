/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import java.util.Map;
import java.util.function.Function;

/**
 * Configures the behavior of a single verifier.
 */
public interface Configuration
{
	/**
	 * Indicates if if {@code assertThat()} should delegate to {@code requireThat()}; otherwise, it won't
	 * do anything.
	 *
	 * @return true if assertions are enabled for
	 * {@code org.bitbucket.cowwoc.requirements.java.internal.scope.MainConfiguration}
	 */
	boolean assertionsAreEnabled();

	/**
	 * Indicates that {@code assertThat()} should invoke {@code requireThat()}.
	 *
	 * @return this
	 */
	Configuration withAssertionsEnabled();

	/**
	 * Indicates that {@code assertThat()} shouldn't do anything.
	 *
	 * @return this
	 */
	Configuration withAssertionsDisabled();

	/**
	 * Indicates if exceptions should show the difference between the actual and expected values.
	 *
	 * @return true by default
	 */
	boolean isDiffEnabled();

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return this
	 */
	Configuration withDiff();

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected
	 * values.
	 *
	 * @return this
	 */
	Configuration withoutDiff();

	/**
	 * Returns an unmodifiable map to append to the exception message.
	 *
	 * @return an empty map by default
	 * @see #putContext(String, Object)
	 */
	Map<String, Object> getContext();

	/**
	 * Adds or updates contextual information associated with the exception message. Overrides any values
	 * associated with the {@code name} at the {@link ThreadRequirements} level.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	Configuration putContext(String name, Object value);

	/**
	 * Removes contextual information associated with the exception message.
	 *
	 * @param name the name of the parameter
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	Configuration removeContext(String name);

	/**
	 * Returns the {@code String} representation of an object. By default, custom handlers are provided for
	 * arrays, {@code Integer}, {@code Long}, {@code BigDecimal}, and {@code Path}.
	 *
	 * @param o an object
	 * @return the {@code String} representation of the object
	 * @see #withStringConverter(Class, Function)
	 */
	String toString(Object o);

	/**
	 * Indicates that a function should be used to convert an object to a String.
	 * <p>
	 * Please note that {@code type} must be an exact match (e.g. configuring a converter for
	 * {@code Set} will not match values of type {@code HashSet}).
	 *
	 * @param <T>       the type of object being converted
	 * @param type      the type of object being converted (non-primitive arrays are mapped to
	 *                  {@code Object[].class})
	 * @param converter a function that converts an object of the specified type to a String
	 * @return this
	 * @throws NullPointerException if any of the arguments are null
	 */
	<T> Configuration withStringConverter(Class<T> type, Function<T, String> converter);

	/**
	 * Indicates that an object's {@code toString()} method should be used to convert it to a String.
	 *
	 * @param <T>  the type of object being converted
	 * @param type the type of object being converted
	 * @return this
	 * @throws NullPointerException if {@code type} is null
	 */
	<T> Configuration withoutStringConverter(Class<T> type);

	/**
	 * Replaces a verifier's configuration.
	 *
	 * @param configuration a new configuration
	 * @return this
	 * @throws NullPointerException if {@code configuration} is null
	 */
	Configuration withConfiguration(Configuration configuration);
}
