package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.util.Exceptions;

import java.util.List;
import java.util.function.Function;

/**
 * A collection of validation failures.
 */
public final class ValidationFailures
{
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
		this.failures = failures;
	}

	/**
	 * Throws an exception if a validation failed; otherwise, returns {@code true}.
	 *
	 * @return true if the validation passed
	 * @throws RuntimeException          if a method precondition was violated
	 * @throws Error                     if a class invariant or method postcondition was violated
	 * @throws MultipleFailuresException if more than one validation failed. This exception contains a list of
	 *                                   the failures.
	 * @see ConfigurationUpdater#exceptionTransformer(Function) Changing the type of exception that is thrown
	 */
	public boolean throwOnFailure()
	{
		if (failures.isEmpty())
			return true;
		Throwable throwable;
		if (failures.size() == 1)
		{
			ValidationFailure failure = failures.getFirst();
			throwable = failure.getException();
		}
		else
			throwable = new MultipleFailuresException(failures);
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
	 * Returns an unmodifiable list of validation failures.
	 *
	 * @return an unmodifiable list of validation failures
	 */
	public List<ValidationFailure> getFailures()
	{
		return failures;
	}
}