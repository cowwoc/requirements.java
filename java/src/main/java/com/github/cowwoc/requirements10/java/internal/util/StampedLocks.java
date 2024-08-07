package com.github.cowwoc.requirements10.java.internal.util;

import com.github.cowwoc.pouch.core.WrappedCheckedException;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.StampedLock;

/**
 * Helper functions for {@code StampedLock}s.
 *
 * @see <a
 * href="https://www.javaspecialists.eu/talks/pdfs/2014%20JavaLand%20in%20Germany%20-%20%22Java%208%20From%20Smile%20To%20Tears%20-%20Emotional%20StampedLock%22%20by%20Heinz%20Kabutz.pdf">Excellent
 * overview of StampedLock</a>
 */
public final class StampedLocks
{
	private StampedLocks()
	{
	}

	/**
	 * Acquires an optimistic read lock and runs a task. If the optimistic lock is invalidated, re-runs the task
	 * using a read lock.
	 * <p>
	 * {@code task} is guaranteed to be invoked <i>at least</i> once, but must be safe to invoke multiple times
	 * as well. The return value must correspond to a local copy of the fields being read as there is no
	 * guarantee that the state won't change between the time the lock is released and the time that the value
	 * is returned.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param lock a lock
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if any of the arguments are null
	 * @throws WrappedCheckedException if any checked exceptions are thrown
	 */
	public static <V> V optimisticRead(StampedLock lock, Callable<V> task)
	{
		long stamp = lock.tryOptimisticRead();
		if (stamp != 0)
		{
			V result = runTask(task);
			if (lock.validate(stamp))
				return result;
		}
		return read(lock, task);
	}

	/**
	 * Acquires a read lock and runs a task.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param lock a lock
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if any of the arguments are null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	public static <V> V read(StampedLock lock, Callable<V> task)
	{
		long stamp = lock.readLock();
		try
		{
			return runTask(task);
		}
		finally
		{
			lock.unlockRead(stamp);
		}
	}

	/**
	 * Acquires a write lock and runs a task.
	 *
	 * @param lock a lock
	 * @param task the task to run while holding the lock
	 * @return a write lock as a resource
	 * @throws NullPointerException if {@code lock} is null
	 */
	public static <V> V write(StampedLock lock, Runnable task)
	{
		return write(lock, () ->
		{
			task.run();
			return null;
		});
	}

	/**
	 * Acquires a write lock and runs a task.
	 *
	 * @param lock a lock
	 * @param task the task to run while holding the lock
	 * @return a write lock as a resource
	 * @throws NullPointerException if {@code lock} is null
	 */
	public static <V> V write(StampedLock lock, Callable<V> task)
	{
		long stamp = lock.writeLock();
		try
		{
			return runTask(task);
		}
		finally
		{
			lock.unlockWrite(stamp);
		}
	}

	/**
	 * Runs a task.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	private static <V> V runTask(Callable<V> task)
	{
		try
		{
			return task.call();
		}
		catch (Exception e)
		{
			throw WrappedCheckedException.wrap(e);
		}
	}
}