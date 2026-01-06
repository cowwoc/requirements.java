/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java;

import io.github.cowwoc.requirements13.java.internal.util.Exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of validation failures.
 */
public final class ValidationFailures
{
	/**
	 * A collection that does not contain any failures.
	 */
	public static final ValidationFailures EMPTY = new ValidationFailures(true, List.of());
	private final boolean cleanStackTrace;
	private final List<ValidationFailure> failures;

	/**
	 * Creates a new instance.
	 *
	 * @param cleanStackTrace {@code true} if stack traces may be modified, {@code false} otherwise
	 * @param failures        the validation failures
	 * @throws NullPointerException if {@code failures} is null
	 */
	public ValidationFailures(boolean cleanStackTrace, List<ValidationFailure> failures)
	{
		if (failures == null)
			throw new NullPointerException("failures may not be null");
		this.cleanStackTrace = cleanStackTrace;
		this.failures = new ArrayList<>(failures);
	}

	/**
	 * Checks if any validation has failed.
	 *
	 * @return {@code false} if at least one validation has failed
	 */
	public boolean isEmpty()
	{
		return failures.isEmpty();
	}

	/**
	 * Returns the list of failed validations.
	 *
	 * @return an unmodifiable list of failed validations
	 */
	public List<ValidationFailure> getFailures()
	{
		return List.copyOf(failures);
	}

	/**
	 * Throws an exception if a validation failed; otherwise, returns {@code true}.
	 *
	 * @return true if the validation passed
	 * @throws RuntimeException          if a method precondition was violated
	 * @throws Error                     if a class invariant or method postcondition was violated
	 * @throws MultipleFailuresException if more than one validation failed. This exception contains a list of
	 *                                   the failures
	 */
	public boolean throwOnFailure()
	{
		if (failures.isEmpty())
			return true;
		Throwable throwable = getThrowable();
		if (cleanStackTrace)
			Exceptions.removeLibraryFromStackTrace(throwable);
		switch (throwable)
		{
			case RuntimeException e -> throw e;
			case Error e -> throw e;
			default -> throw new AssertionError(throwable);
		}
	}

	/**
	 * @return the failure exception
	 */
	private Throwable getThrowable()
	{
		if (failures.size() == 1)
		{
			ValidationFailure failure = failures.getFirst();
			return failure.getException();
		}
		return new MultipleFailuresException(failures);
	}

	/**
	 * Returns the validation failure messages.
	 *
	 * @return an empty list if the validation was successful
	 */
	public List<String> getMessages()
	{
		List<String> messages = new ArrayList<>(failures.size());
		for (ValidationFailure failure : failures)
			messages.add(failure.getMessage());
		return messages;
	}

	/**
	 * Returns the exception for the validation failures, if any.
	 *
	 * <ol>
	 *   <li>Returns {@code null} if no validation has failed.</li>
	 *   <li>Returns {@code MultipleFailuresException} if there were multiple failures.</li>
	 *   <li>Returns {@code Throwable} if there was one failure.</li>
	 * </ol>
	 *
	 * @return the exception or {@code null} if no validation has failed
	 */
	public Throwable getException()
	{
		if (failures.isEmpty())
			return null;
		Throwable throwable = getThrowable();
		if (cleanStackTrace)
			Exceptions.removeLibraryFromStackTrace(throwable);
		return throwable;
	}

	/**
	 * Adds validation failures into this collection.
	 *
	 * @param failures the failures to add
	 * @return this
	 * @throws NullPointerException if {@code failures} is null
	 */
	public ValidationFailures addAll(ValidationFailures failures)
	{
		if (failures == null)
			throw new NullPointerException("failures may not be null");
		this.failures.addAll(failures.failures);
		return this;
	}
}