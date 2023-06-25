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
	 * @param message the detail message
	 * @param cause   the exception to wrap
	 * @throws NullPointerException if any of the arguments are null
	 */
	private WrappedCheckedException(String message, Throwable cause)
	{
		super(message, cause);
		if (message == null)
			throw new NullPointerException("message may not be null");
		if (cause == null)
			throw new NullPointerException("cause may not be null");
	}

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
	 * Wraps any checked exceptions thrown by a callable.
	 *
	 * @param callable the task to execute
	 * @param <V>      the type of value returned by {@code callable}
	 * @return the value returned by {@code callable}
	 * @throws NullPointerException if {@code callable} is null
	 */
	public static <V> V wrap(Callable<V> callable)
	{
		try
		{
			return callable.call();
		}
		catch (Exception e)
		{
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task.
	 *
	 * @param task the task to execute
	 * @throws NullPointerException if {@code task} is null
	 */
	public static void wrap(Task task)
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task. It rethrows the expected checked exceptions and wraps the
	 * rest in a {@code WrappedCheckedException}.
	 *
	 * @param <E1>      a checked exception that may be thrown by the task
	 * @param task      the task to execute
	 * @param expected1 a checked exception that may be thrown by the task
	 * @throws NullPointerException if any of the arguments are null
	 * @throws E1                   a checked exception that may be thrown by the task
	 */
	public static <E1 extends Exception> void wrap(Task task, Class<E1> expected1) throws E1
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			if (expected1.isInstance(e))
				throw expected1.cast(e);
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task. It rethrows the expected checked exceptions and wraps the
	 * rest in a {@code WrappedCheckedException}.
	 *
	 * @param <E1>      a checked exception that may be thrown by the task
	 * @param <E2>      a checked exception that may be thrown by the task
	 * @param task      the task to execute
	 * @param expected1 a checked exception that may be thrown by the task
	 * @param expected2 a checked exception that may be thrown by the task
	 * @throws NullPointerException if any of the arguments are null
	 * @throws E1                   a checked exception that may be thrown by the task
	 * @throws E2                   a checked exception that may be thrown by the task
	 */
	public static <E1 extends Exception, E2 extends Exception> void wrap(Task task, Class<E1> expected1,
		Class<E2> expected2) throws E1, E2
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			if (expected1.isInstance(e))
				throw expected1.cast(e);
			if (expected2.isInstance(e))
				throw expected2.cast(e);
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task. It rethrows the expected checked exceptions and wraps the
	 * rest in a {@code WrappedCheckedException}.
	 *
	 * @param <E1>       a checked exception that may be thrown by the task
	 * @param <E2>       a checked exception that may be thrown by the task
	 * @param <E3>       a checked exception that may be thrown by the task
	 * @param task       the task to execute
	 * @param exception1 a checked exception that may be thrown by the task
	 * @param exception2 a checked exception that may be thrown by the task
	 * @param exception3 a checked exception that may be thrown by the task
	 * @throws NullPointerException if any of the arguments are null
	 * @throws E1                   a checked exception that may be thrown by the task
	 * @throws E2                   a checked exception that may be thrown by the task
	 * @throws E3                   a checked exception that may be thrown by the task
	 */
	public static <E1 extends Exception, E2 extends Exception, E3 extends Exception> void wrap(
		Task task, Class<E1> exception1, Class<E2> exception2, Class<E3> exception3)
		throws E1, E2, E3
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			if (exception1.isInstance(e))
				throw exception1.cast(e);
			if (exception2.isInstance(e))
				throw exception2.cast(e);
			if (exception3.isInstance(e))
				throw exception3.cast(e);
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task. It rethrows the expected checked exceptions and wraps the
	 * rest in a {@code WrappedCheckedException}.
	 *
	 * @param <E1>      a checked exception that may be thrown by the task
	 * @param <E2>      a checked exception that may be thrown by the task
	 * @param <E3>      a checked exception that may be thrown by the task
	 * @param <E4>      a checked exception that may be thrown by the task
	 * @param task      the task to execute
	 * @param expected1 a checked exception that may be thrown by the task
	 * @param expected2 a checked exception that may be thrown by the task
	 * @param expected3 a checked exception that may be thrown by the task
	 * @param expected4 a checked exception that may be thrown by the task
	 * @throws NullPointerException if any of the arguments are null
	 * @throws E1                   a checked exception that may be thrown by the task
	 * @throws E2                   a checked exception that may be thrown by the task
	 * @throws E3                   a checked exception that may be thrown by the task
	 * @throws E4                   a checked exception that may be thrown by the task
	 */
	public static <E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception>
	void wrap(Task task, Class<E1> expected1, Class<E2> expected2, Class<E3> expected3, Class<E4> expected4)
		throws E1, E2, E3, E4
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			if (expected1.isInstance(e))
				throw expected1.cast(e);
			if (expected2.isInstance(e))
				throw expected2.cast(e);
			if (expected3.isInstance(e))
				throw expected3.cast(e);
			if (expected4.isInstance(e))
				throw expected4.cast(e);
			throw WrappedCheckedException.wrap(e);
		}
	}

	/**
	 * Wraps any checked exceptions thrown by a task. It rethrows the expected checked exceptions and wraps the
	 * rest in a {@code WrappedCheckedException}.
	 *
	 * @param <E1>      a checked exception that may be thrown by the task
	 * @param <E2>      a checked exception that may be thrown by the task
	 * @param <E3>      a checked exception that may be thrown by the task
	 * @param <E4>      a checked exception that may be thrown by the task
	 * @param <E5>      a checked exception that may be thrown by the task
	 * @param task      the task to execute
	 * @param expected1 a checked exception that may be thrown by the task
	 * @param expected2 a checked exception that may be thrown by the task
	 * @param expected3 a checked exception that may be thrown by the task
	 * @param expected4 a checked exception that may be thrown by the task
	 * @param expected5 a checked exception that may be thrown by the task
	 * @throws NullPointerException if any of the arguments are null
	 * @throws E1                   a checked exception that may be thrown by the task
	 * @throws E2                   a checked exception that may be thrown by the task
	 * @throws E3                   a checked exception that may be thrown by the task
	 * @throws E4                   a checked exception that may be thrown by the task
	 * @throws E5                   a checked exception that may be thrown by the task
	 */
	public static <E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception,
		E5 extends Exception> void wrap(Task task, Class<E1> expected1, Class<E2> expected2, Class<E3> expected3,
		Class<E4> expected4, Class<E5> expected5) throws E1, E2, E3, E4, E5
	{
		try
		{
			task.run();
		}
		catch (Exception e)
		{
			if (expected1.isInstance(e))
				throw expected1.cast(e);
			if (expected2.isInstance(e))
				throw expected2.cast(e);
			if (expected3.isInstance(e))
				throw expected3.cast(e);
			if (expected4.isInstance(e))
				throw expected4.cast(e);
			if (expected5.isInstance(e))
				throw expected5.cast(e);
			throw WrappedCheckedException.wrap(e);
		}
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
	 * Wraps an exception, unless it is a {@code RuntimeException}.
	 *
	 * @param message the detail message of the WrappedCheckedException
	 * @param t       the exception to wrap
	 * @return the updated exception
	 * @throws NullPointerException if {@code t} is null
	 */
	public static RuntimeException wrap(String message, Throwable t)
	{
		if (t instanceof RuntimeException re)
			return re;
		if (t instanceof ExecutionException ee)
			return wrap(message, ee.getCause());
		return new WrappedCheckedException(message, t);
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