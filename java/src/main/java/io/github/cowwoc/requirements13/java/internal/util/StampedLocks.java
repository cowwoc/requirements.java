/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.util;

import io.github.cowwoc.pouch.core.WrappedCheckedException;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

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
	 * @param task the task to run
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
	 * @param task the task to run
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
	 * @param task the task to run
	 * @throws NullPointerException if any of the arguments are null
	 */
	public static void write(StampedLock lock, Runnable task)
	{
		long stamp = lock.writeLock();
		try
		{
			runTask(task);
		}
		finally
		{
			lock.unlockWrite(stamp);
		}
	}

	/**
	 * Acquires a write lock and runs a task.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param lock a lock
	 * @param task the task to run
	 * @return the value returned by the task
	 * @throws NullPointerException if any of the arguments are null
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
	 * @param task the task to run
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	private static void runTask(Runnable task)
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
	 * Runs a task.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param task the task to run
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
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

	/**
	 * Returns a value, initializing it if it hasn't been initialized yet.
	 *
	 * @param <V>         the type of value returned by the task
	 * @param lock        the lock used to ensure thread safety
	 * @param supplier    returns the value, or {@code null} if it hasn't been initialized yet
	 * @param initializer initializes the value if it hasn't been initialized yet and returns it
	 * @return the initialized value
	 * @throws NullPointerException if any of the arguments are null
	 */
	public static <V> V getLazilyInitializedValue(StampedLock lock, Supplier<V> supplier,
		Callable<V> initializer)
	{
		long stamp = lock.tryOptimisticRead();
		if (stamp == 0)
			return getLazilyInitializedValueUsingRead(lock, supplier, initializer);
		V value = supplier.get();
		if (!lock.validate(stamp))
			return getLazilyInitializedValueUsingRead(lock, supplier, initializer);
		if (value != null)
			return value;
		stamp = lock.tryConvertToWriteLock(stamp);
		if (stamp == 0)
			return getLazilyInitializedValueUsingWrite(lock, supplier, initializer);
		try
		{
			return runTask(initializer);
		}
		finally
		{
			lock.unlockWrite(stamp);
		}
	}

	/**
	 * Returns a value, initializing it if it hasn't been initialized yet, using a read lock.
	 *
	 * @param <V>         the type of value returned by the task
	 * @param lock        the lock used to ensure thread safety
	 * @param supplier    returns the value, or {@code null} if it hasn't been initialized yet
	 * @param initializer initializes the value if it hasn't been initialized yet
	 * @return the initialized value
	 * @throws NullPointerException if any of the arguments are null
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	private static <V> V getLazilyInitializedValueUsingRead(StampedLock lock, Supplier<V> supplier,
		Callable<V> initializer)
	{
		long stamp = lock.readLock();
		V value;
		try
		{
			value = supplier.get();
		}
		catch (RuntimeException e)
		{
			lock.unlockRead(stamp);
			throw e;
		}
		if (value != null)
		{
			lock.unlockRead(stamp);
			return value;
		}
		stamp = lock.tryConvertToWriteLock(stamp);
		if (stamp == 0)
		{
			lock.unlockRead(stamp);
			return getLazilyInitializedValueUsingWrite(lock, supplier, initializer);
		}
		try
		{
			return runTask(initializer);
		}
		finally
		{
			lock.unlockWrite(stamp);
		}
	}

	/**
	 * Returns a value, initializing it if it hasn't been initialized yet, using a write lock.
	 *
	 * @param <V>         the type of value returned by the task
	 * @param lock        the lock used to ensure thread safety
	 * @param supplier    returns the value, or {@code null} if it hasn't been initialized yet
	 * @param initializer initializes the value if it hasn't been initialized yet
	 * @return the value
	 * @throws NullPointerException if any of the arguments are null
	 */
	private static <V> V getLazilyInitializedValueUsingWrite(StampedLock lock, Supplier<V> supplier,
		Callable<V> initializer)
	{
		return write(lock, () ->
		{
			V value = supplier.get();
			if (value != null)
				return value;
			return runTask(initializer);
		});
	}
}