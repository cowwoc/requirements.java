/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.List;
import java.util.Map.Entry;

/**
 * Behavior common to all verifiers.
 * <p>
 * Interfaces that extend {@code Verifier} may be implemented, but may not be extended.
 * Interfaces that do not extend {@code Verifier} may be extended, but may not be implemented.
 * <p>
 * All implementations must be immutable and final.
 *
 * @param <S> the type of the verifier
 * @author Gili Tzabari
 */
public interface Verifier<S>
{
	/**
	 * Overrides the type of exception that will get thrown if a requirement fails.
	 * <p>
	 * The exception class must define the following constructors:
	 * <p>
	 * {@code <init>(String message)}<br>
	 * {@code <init>(String message, Throwable cause)}
	 *
	 * @param exception the type of exception to throw, {@code null} to throw the default exception
	 *                  type
	 * @return a verifier with the specified exception override
	 */
	S withException(Class<? extends RuntimeException> exception);

	/**
	 * Appends contextual information to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return a new verifier with the specified context
	 * @throws NullPointerException if {@code key} is null
	 */
	S addContext(String key, Object value);

	/**
	 * Sets the contextual information to append to the exception message.
	 *
	 * @param context the contextual information
	 * @return a verifier with the specified context
	 * @throws NullPointerException if {@code context} is null
	 */
	S withContext(List<Entry<String, Object>> context);
}
