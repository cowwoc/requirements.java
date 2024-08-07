/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.util.Exceptions;

import java.util.Set;
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
	private final Set<Class<? extends Throwable>> checkedExceptions;

	/**
	 * @param configuration     the validator's configuration
	 * @param message           the failure message
	 * @param cause             (optional) the underlying cause of the exception, or {@code null} if undefined
	 * @param exceptionBuilder  returns the exception associated with the failure message
	 * @param checkedExceptions the checked exceptions that are thrown by the validation method
	 * @throws AssertionError if:
	 *                        <ul>
	 *                          <li>{@code configuration}, {@code message}, {@code exceptionBuilder} or
	 *                          {@code checkedExceptions} are null</li>
	 *                          <li>{@code message} is blank</li>
	 *                        </ul>
	 */
	public ValidationFailureImpl(Configuration configuration, String message, Throwable cause,
		ExceptionBuilder exceptionBuilder, Set<Class<? extends Throwable>> checkedExceptions)
	{
		assert (configuration != null);
		assert (exceptionBuilder != null);
		assert (message != null);
		assert (!message.isBlank()) : "message may not be blank";
		assert (checkedExceptions != null);

		this.exceptionTransformer = configuration.exceptionTransformer();
		this.needToCleanStackTrace = configuration.cleanStackTrace();
		if (configuration.recordStacktrace())
		{
			this.message = null;
			this.cause = null;
			this.exceptionBuilder = null;
			this.throwable = exceptionBuilder.apply(message, cause);
		}
		else
		{
			this.message = message;
			this.cause = cause;
			this.exceptionBuilder = exceptionBuilder;
		}
		this.checkedExceptions = Set.copyOf(checkedExceptions);
	}

	@Override
	public String getMessage()
	{
		if (throwable != null)
			return throwable.getMessage();
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
				!(transformedThrowable instanceof Error) &&
				!checkedExceptions.contains(transformedThrowable.getClass()))
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