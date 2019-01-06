/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import java.util.function.Function;

/**
 * Configures a requirements verifier.
 */
public interface Configurable
{
	/**
	 * @return true if {@code assertThat()} should delegate to {@code requireThat()}; false if it
	 * shouldn't do anything
	 */
	boolean assertionsAreEnabled();

	/**
	 * Indicates that {@code assertThat()} should invoke {@code requireThat()}.
	 *
	 * @return a verifier with the updated configuration
	 */
	Configurable withAssertionsEnabled();

	/**
	 * Indicates that {@code assertThat()} shouldn't do anything.
	 *
	 * @return a verifier with the updated configuration
	 */
	Configurable withAssertionsDisabled();

	/**
	 * Overrides the type of exception that will get thrown if a verification fails.
	 * <p>
	 * The exception class must define the following constructors:
	 * <p>
	 * {@code <init>(String message)}<br>
	 * {@code <init>(String message, Throwable cause)}
	 *
	 * @param exception the type of exception to throw
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code exception} is null
	 */
	Configurable withException(Class<? extends RuntimeException> exception);

	/**
	 * Throws the default exception type if a verification fails.
	 *
	 * @return a verifier with the updated configuration
	 */
	Configurable withDefaultException();

	/**
	 * Indicates that exceptions should show the difference between the actual and expected values.
	 *
	 * @return a verifier with the updated configuration
	 */
	Configurable withDiff();

	/**
	 * Indicates that exceptions should not show the difference between the actual and expected
	 * values.
	 *
	 * @return a verifier with the updated configuration
	 */
	Configurable withoutDiff();

	/**
	 * Appends contextual information to the exception message.
	 *
	 * @param name  the name of the value
	 * @param value a value
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code name} is null
	 */
	Configurable addContext(String name, Object value);

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
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if any of the arguments are null
	 */
	<T> Configurable withStringConverter(Class<T> type, Function<T, String> converter);

	/**
	 * Indicates that an object's {@code toString()} method should be used to convert it to a String.
	 *
	 * @param <T>  the type of object being converted
	 * @param type the type of object being converted
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code type} is null
	 */
	<T> Configurable withoutStringConverter(Class<T> type);

	/**
	 * @return the configuration associated with the verifier
	 */
	Configuration getConfiguration();

	/**
	 * @param configuration a new configuration
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	Configurable withConfiguration(Configuration configuration);

	/**
	 * @return the global configuration shared by all verifiers
	 */
	GlobalConfigurable getGlobalConfiguration();
}
