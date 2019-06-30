/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ValidationFailure;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Default implementation of {@code ValidationFailure}.
 */
public final class ValidationFailureImpl implements ValidationFailure
{
	private final Class<? extends Exception> exceptionType;
	private Throwable cause;
	private final String message;
	private final List<Entry<String, Object>> context = new ArrayList<>();

	/**
	 * @param exceptionType the type of exception associated with the failure
	 * @param message       the exception message associated with the failure
	 * @throws NullPointerException     if {@code exceptionType} or {@code message} are null
	 * @throws IllegalArgumentException if {@code message} is empty
	 */
	public ValidationFailureImpl(Class<? extends Exception> exceptionType, String message)
	{
		assert (exceptionType != null);
		assert (message != null);
		assert (!message.trim().isEmpty()) : "message may not be empty";

		this.exceptionType = exceptionType;
		this.message = message;
	}

	/**
	 * Returns the type of exception associated with the failure.
	 *
	 * @return the type of exception associated with the failure
	 */
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

	@Override
	public String getMessage()
	{
		return message;
	}

	/**
	 * Returns the list of name-value pairs to append to the exception message.
	 *
	 * @return the list of name-value pairs to append to the exception message
	 */
	public List<Entry<String, Object>> getContext()
	{
		return context;
	}

	/**
	 * Adds contextual information associated with the failure.
	 *
	 * @param name  the name of a variable
	 * @param value the value of the variable
	 * @return this
	 * @throws NullPointerException if {@code name} is null
	 */
	public ValidationFailureImpl addContext(String name, Object value)
	{
		assert (name != null) : "name may not be null";
		context.add(new SimpleImmutableEntry<>(name, value));
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
		assert (context != null) : "context may not be null";
		this.context.addAll(context);
		return this;
	}

	@Override
	public String toString()
	{
		return "exceptionType: " + exceptionType + ", message: " + message + ", context: " + context;
	}
}