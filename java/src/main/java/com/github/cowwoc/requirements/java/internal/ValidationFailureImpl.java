/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.diff.ContextLine;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of {@code ValidationFailure}.
 */
public final class ValidationFailureImpl implements ValidationFailure
{
	private final ApplicationScope scope;
	private final Configuration config;
	private final Class<? extends Exception> exceptionType;
	private final String message;
	private String messageWithContext;
	private Throwable cause;
	/**
	 * The contextual information associated with the exception (name-value pairs).
	 */
	private final List<ContextLine> context = new ArrayList<>(2);

	/**
	 * @param scope         the application configuration
	 * @param config        the instance configuration
	 * @param exceptionType the type of exception associated with the failure
	 * @param message       the message associated with the failure
	 * @throws NullPointerException     if {@code validator}, {@code exceptionType} or {@code message} are null
	 * @throws IllegalArgumentException if {@code message} is blank
	 */
	public ValidationFailureImpl(ApplicationScope scope, Configuration config,
		Class<? extends Exception> exceptionType, String message)
	{
		assert (scope != null);
		assert (config != null);
		assert (exceptionType != null);
		assert (message != null);
		assert (!message.isBlank()) : "message may not be blank";

		this.scope = scope;
		this.config = config;
		this.exceptionType = exceptionType;
		this.message = message;
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
		return cause;
	}

	/**
	 * Sets the underlying cause of the exception.
	 *
	 * @param cause the underlying cause of the exception
	 * @return this
	 */
	public ValidationFailureImpl setCause(Throwable cause)
	{
		this.cause = cause;
		return this;
	}

	/**
	 * Adds contextual information associated with the failure.
	 *
	 * @param name  the name of a variable
	 * @param value the value of the variable
	 * @return this
	 * @throws AssertionError if the key is null, blank or contains a colon
	 */
	public ValidationFailureImpl addContext(String name, Object value)
	{
		context.add(new ContextLine(name, config.toString(value)));
		messageWithContext = null;
		return this;
	}

	/**
	 * Adds contextual information to append to the message.
	 *
	 * @param context the list of name-value pairs to append to the exception message
	 * @return this
	 * @throws NullPointerException if {@code context} is null
	 */
	public ValidationFailureImpl addContext(List<ContextLine> context)
	{
		assert (context != null) : "context may not be null";
		this.context.addAll(context);
		messageWithContext = null;
		return this;
	}

	@Override
	public String getMessage()
	{
		if (messageWithContext == null)
			this.messageWithContext = scope.getExceptions().getContextMessage(config, message, context);
		return messageWithContext;
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
		Exceptions exceptions = scope.getExceptions();
		boolean cleanStackTrace = scope.getGlobalConfiguration().isCleanStackTrace();
		return exceptions.createException(type, getMessage(), cause, cleanStackTrace);
	}

	@Override
	public String toString()
	{
		return "exceptionType: " + exceptionType + ", message: " + getMessage() + ", cause: " + cause;
	}
}