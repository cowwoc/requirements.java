package com.github.cowwoc.requirements11.java;

import java.io.Serial;
import java.util.List;

/**
 * Thrown if multiple validations have failed.
 */
public final class MultipleFailuresException extends RuntimeException
{
	@Serial
	private static final long serialVersionUID = 0L;
	private transient final List<ValidationFailure> failures;

	/**
	 * Creates a new exception.
	 *
	 * @param failures the list of validation failures
	 * @throws NullPointerException     if {@code failures} is null
	 * @throws IllegalArgumentException if {@code failures} contains less than two elements
	 */
	public MultipleFailuresException(List<ValidationFailure> failures)
	{
		super(createMessage(failures));
		this.failures = List.copyOf(failures);
	}

	private static String createMessage(List<ValidationFailure> failures)
	{
		if (failures == null)
			throw new NullPointerException("failures may not be null");
		if (failures.isEmpty())
			throw new IllegalArgumentException("failures must contain at least two elements");
		StringBuilder result = new StringBuilder(35).append("There are ").append(failures.size()).
			append(" nested exceptions.\n");
		int i = 1;
		for (ValidationFailure failure : failures)
		{
			result.append(i).append(". ").append(failure.getException().getClass().getName());
			String message = failure.getMessage();
			if (message != null)
			{
				result.append(": ").
					append(message).
					append('\n');
			}
			++i;
		}
		return result.toString();
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