package com.github.cowwoc.requirements.java;

import java.io.Serial;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * A runtime exception dedicated to wrapping checked exceptions.
 */
public final class WrappedCheckedException extends RuntimeException
{
	@Serial
	private static final long serialVersionUID = 0L;

	/**
	 * Wraps an exception.
	 *
	 * @param cause the exception to wrap
	 * @throws NullPointerException if {@code cause} is null
	 */
	private WrappedCheckedException(Throwable cause)
	{
		super(cause);
		if (cause == null)
			throw new NullPointerException("cause may not be null");
	}
	
	/**
	 * Wraps an exception, unless it is a {@code RuntimeException}.
	 *
	 * @param t the exception to wrap
	 * @return the updated exception
	 * @throws NullPointerException if {@code t} is null
	 */
	public static RuntimeException wrap(Throwable t)
	{
		if (t instanceof RuntimeException re)
			return re;
		if (t instanceof ExecutionException ee)
			return wrap(ee.getCause());
		return new WrappedCheckedException(t);
	}

	/**
	 * A {@link Callable} without a return value.
	 */
	@FunctionalInterface
	public interface Task
	{
		/**
		 * Runs the task.
		 *
		 * @throws Exception if unable to compute a result
		 */
		void run() throws Exception;
	}
}