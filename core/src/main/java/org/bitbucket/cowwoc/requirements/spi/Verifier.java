/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.annotations.Beta;

/**
 * Behavior common to all verifiers.
 * <p>
 * All implementations must be immutable and final.
 *
 * @author Gili Tzabari
 */
public interface Verifier
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
	Verifier withException(Class<? extends RuntimeException> exception);

	/**
	 * Appends contextual information to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return a verifier with the specified context
	 * @throws NullPointerException if {@code key} is null
	 */
	@Beta
	Verifier addContext(String key, Object value);

	/**
	 * Sets the contextual information to append to the exception message.
	 *
	 * @param context the contextual information
	 * @return a verifier with the specified context
	 * @throws NullPointerException if {@code context} is null
	 */
	@Beta
	Verifier withContext(List<Entry<String, Object>> context);
}
