package com.github.cowwoc.requirements10.java.internal.validator;

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