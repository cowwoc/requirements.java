/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

/**
 * A verifier whose behavior is configurable.
 *
 * @param <S> the type of the verifier
 * @author Gili Tzabari
 */
public interface Configurable<S>
{
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
	S withException(Class<? extends RuntimeException> exception);

	/**
	 * Throws the default exception type if a verification fails.
	 *
	 * @return a verifier with the updated configuration
	 */
	S withDefaultException();

	/**
	 * Appends contextual information to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return a verifier with the updated configuration
	 * @throws NullPointerException if {@code key} is null
	 */
	S addContext(String key, Object value);
}
