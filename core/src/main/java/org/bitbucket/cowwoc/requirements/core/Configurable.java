/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

/**
 * A verifier whose behavior is configurable.
 *
 * @author Gili Tzabari
 */
public interface Configurable
{
	/**
	 * @return true if {@code assertThat()} should delegate to {@code requireThat()}; false if it
	 *         shouldn't do anything
	 * @deprecated this method is scheduled for removal without replacement
	 */
	@Deprecated
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
	 * @param key   a key
	 * @param value a value
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code key} is null
	 */
	Configurable addContext(String key, Object value);

	/**
	 * @param configuration a new configuration
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	Configurable withConfiguration(Configuration configuration);
}
