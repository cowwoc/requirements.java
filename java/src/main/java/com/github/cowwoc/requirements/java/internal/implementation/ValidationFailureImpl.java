/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;

import java.util.function.Function;

public final class ValidationFailureImpl implements ValidationFailure
{
	private final String message;
	private final Throwable cause;
	private final ExceptionBuilder exceptionBuilder;
	private Throwable throwable;
	private final Function<Throwable, ? extends Throwable> exceptionTransformer;
	private Throwable transformedThrowable;
	private boolean needToCleanStackTrace;

	/**
	 * @param message          the failure message
	 * @param cause            the underlying cause of the exception
	 * @param exceptionBuilder returns the exception associated with the failure message
	 * @throws AssertionError if any of the arguments are null. If the exception message contains leading or
	 *                        trailing whitespace, or is empty.
	 */
	public ValidationFailureImpl(Configuration configuration, String message,
		Throwable cause, ExceptionBuilder exceptionBuilder)
	{
		assert (configuration != null);
		assert (exceptionBuilder != null);
		assert (!message.isBlank()) : "message may not be blank";

		this.exceptionTransformer = configuration.exceptionTransformer();
		this.needToCleanStackTrace = configuration.cleanStackTrace();
		this.message = message;
		this.cause = cause;
		if (configuration.lazyExceptions())
			this.exceptionBuilder = exceptionBuilder;
		else
		{
			this.exceptionBuilder = null;
			this.throwable = exceptionBuilder.apply(message, cause);
		}
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	@Override
	public Class<? extends Throwable> getType()
	{
		return getTransformedThrowable().getClass();
	}

	@Override
	public Throwable getException()
	{
		Throwable transformedThrowable = getTransformedThrowable();
		if (needToCleanStackTrace)
		{
			Exceptions.removeLibraryFromStackTrace(transformedThrowable);
			needToCleanStackTrace = false;
		}
		return transformedThrowable;
	}

	private Throwable getTransformedThrowable()
	{
		if (transformedThrowable == null)
		{
			if (throwable == null)
				this.throwable = exceptionBuilder.apply(message, cause);
			transformedThrowable = exceptionTransformer.apply(throwable);
			if (transformedThrowable == null)
				transformedThrowable = throwable;
			if (!(transformedThrowable instanceof RuntimeException) &&
			    !(transformedThrowable instanceof Error))
			{
				transformedThrowable = WrappedCheckedException.wrap(transformedThrowable);
			}
		}
		return transformedThrowable;
	}

	@Override
	public String toString()
	{
		return "throwable: " + throwable + ", transformedThrowable: " + getTransformedThrowable();
	}
}