/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

/**
 * Creates a new exception.
 */
@FunctionalInterface
public interface ExceptionBuilder
{
	/**
	 * Creates a new exception.
	 *
	 * @param message a message that explains what went wrong
	 * @param cause   (optional) the underlying cause of the exception, or {@code null} if undefined
	 * @return a new exception
	 */
	Throwable apply(String message, Throwable cause);
}