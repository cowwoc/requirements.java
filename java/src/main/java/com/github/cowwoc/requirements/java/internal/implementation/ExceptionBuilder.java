package com.github.cowwoc.requirements.java.internal.implementation;

/**
 * Creates a new exception.
 */
public interface ExceptionBuilder
{
	/**
	 * Creates a new exception.
	 *
	 * @param message a message that explains what went wrong
	 * @param cause   the underlying cause of the exception
	 * @return a new exception
	 */
	Throwable apply(String message, Throwable cause);
}
