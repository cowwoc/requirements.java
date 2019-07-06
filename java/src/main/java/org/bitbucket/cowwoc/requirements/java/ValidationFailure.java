/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

/**
 * A failed validation.
 */
public interface ValidationFailure
{
	/**
	 * Returns the message associated with the failure.
	 *
	 * @return the message associated with the failure
	 */
	String getMessage();

	/**
	 * Returns the type of exception associated with the failure.
	 *
	 * @return the type of exception associated with the failure
	 */
	Class<? extends Exception> getExceptionType();

	/**
	 * Creates an exception containing the failure message.
	 *
	 * @param <E>  the type of the exception
	 * @param type the type of the exception
	 * @return the exception corresponding to the validation failure
	 * @throws NullPointerException if {@code type} is null
	 */
	<E extends Exception> E createException(Class<E> type);
}
