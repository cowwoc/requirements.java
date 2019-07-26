/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.util.List;
import java.util.Map.Entry;

/**
 * Default implementation of {@code ValidationFailure}.
 */
public final class ValidationFailureImpl implements ValidationFailure
{
	private final Class<? extends Exception> exceptionType;
	private final ExceptionBuilder exceptionBuilder;

	/**
	 * @param scope         the application configuration
	 * @param config        the instance configuration
	 * @param exceptionType the type of exception associated with the failure
	 * @param message       the exception message associated with the failure
	 * @throws NullPointerException     if {@code validator}, {@code exceptionType} or {@code message} are null
	 * @throws IllegalArgumentException if {@code message} is empty
	 */
	public ValidationFailureImpl(ApplicationScope scope, Configuration config,
	                             Class<? extends Exception> exceptionType,
	                             String message)
	{
		assert (scope != null);
		assert (config != null);
		assert (exceptionType != null);
		assert (message != null);
		assert (!message.trim().isEmpty()) : "message may not be empty";

		this.exceptionType = exceptionType;
		this.exceptionBuilder = new ExceptionBuilder(scope, config, message);
	}

	@Override
	public Class<? extends Exception> getExceptionType()
	{
		return exceptionType;
	}

	/**
	 * Returns the underlying cause of the exception.
	 *
	 * @return the underlying cause of the exception
	 */
	public Throwable getCause()
	{
		return exceptionBuilder.getCause();
	}

	/**
	 * Sets the underlying cause of the exception.
	 *
	 * @param cause the underlying cause of the exception
	 * @return this
	 */
	public ValidationFailureImpl setCause(Throwable cause)
	{
		exceptionBuilder.setCause(cause);
		return this;
	}

	@Override
	public String getMessage()
	{
		return exceptionBuilder.getMessage();
	}

	/**
	 * Returns the list of name-value pairs to append to the exception message.
	 *
	 * @return the list of name-value pairs to append to the exception message
	 */
	public List<Entry<String, Object>> getContext()
	{
		return exceptionBuilder.getContext();
	}

	/**
	 * Adds contextual information associated with the failure.
	 *
	 * @param name  the name of a variable
	 * @param value the value of the variable
	 * @return this
	 * @throws AssertionError if {@code name} is null or empty
	 */
	public ValidationFailureImpl addContext(String name, Object value)
	{
		exceptionBuilder.addContext(name, value);
		return this;
	}

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param context the list of name-value pairs to append to the exception message
	 * @return this
	 * @throws NullPointerException if {@code context} is null
	 */
	public ValidationFailureImpl addContext(List<Entry<String, Object>> context)
	{
		exceptionBuilder.addContext(context);
		return this;
	}

	/**
	 * Creates an exception that corresponds to with this failure.
	 *
	 * @param <T>  type the type of exception to create
	 * @param type the type of exception to create
	 * @return the created exception
	 */
	public <T extends Exception> T createException(Class<T> type)
	{
		return exceptionBuilder.build(type);
	}

	@Override
	public String toString()
	{
		return "exceptionType: " + exceptionType + ", exceptionBuilder: " + exceptionBuilder;
	}
}