package com.github.cowwoc.requirements.java.internal.util;

import com.github.cowwoc.requirements.java.WrappedCheckedException;
import com.github.cowwoc.requirements.java.WrappedCheckedException.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.StampedLock;

/**
 * A reentrant implementation of a StampedLock that can be used with try-with-resources.
 */
public final class ReentrantStampedLock
{
	// Excellent overview of StampedLock:
	// https://www.javaspecialists.eu/talks/pdfs/2014%20JavaLand%20in%20Germany%20-%20%22Java%208%20From%20Smile%20To%20Tears%20-%20Emotional%20StampedLock%22%20by%20Heinz%20Kabutz.pdf
	private final StampedLock lock = new StampedLock();
	/**
	 * The stamp associated with the current thread. {@code null} if none.
	 */
	private final ThreadLocal<Long> stamp = new ThreadLocal<>();

	/**
	 * Creates a new lock.
	 */
	public ReentrantStampedLock()
	{
	}

	private static void doNotUnlock()
	{
	}

	/**
	 * Acquires an optimistic read-lock and runs a task. If an optimistic read-lock cannot be acquired, invokes
	 * {@link #read(Callable) readLock(task)} instead.
	 * <p>
	 * {@code task} is guaranteed to be invoked <i>at least</i> once, but must be safe to invoke multiple times
	 * as well. The return value must correspond to a local copy of the fields being read as there is no
	 * guarantee that state won't change between the time the lock is released and the time that the value is
	 * returned.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if any checked exceptions are thrown
	 */
	public <V> V optimisticRead(Callable<V> task)
	{
		Long existingStamp = this.stamp.get();
		if (existingStamp != null)
		{
			if (StampedLock.isOptimisticReadStamp(existingStamp))
				return runWithOptimisticRead(task, existingStamp);
			if (StampedLock.isLockStamp(existingStamp))
				return runTask(task);
		}
		long stamp = lock.tryOptimisticRead();
		try
		{
			this.stamp.set(stamp);
			return runWithOptimisticRead(task, stamp);
		}
		finally
		{
			// There is nothing to unlock for optimistic reads
			this.stamp.set(existingStamp);
		}
	}

	/**
	 * Acquires an optimistic read-lock and runs a task. If an optimistic read-lock cannot be acquired, invokes
	 * {@link #read(Task) readLock(task)} instead.
	 * <p>
	 * {@code task} is guaranteed to be invoked <i>at least</i> once, but must be safe to invoke multiple times
	 * as well. The return value must correspond to a local copy of the fields being read as there is no
	 * guarantee that state won't change between the time the lock is released and the time that the value is
	 * returned.
	 *
	 * @param task the task to run while holding the lock
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if any checked exceptions are thrown
	 */
	public void optimisticRead(Task task)
	{
		optimisticRead(() ->
		{
			task.run();
			return null;
		});
	}

	/**
	 * Acquires an optimistic read-lock and runs a task. If an optimistic read-lock cannot be acquired, invokes
	 * {@link #read(Callable) readLock(task)} instead.
	 * <p>
	 * {@code task} is guaranteed to be invoked <i>at least</i> once, but must be safe to invoke multiple times
	 * as well. The return value must correspond to a local copy of the fields being read as there is no
	 * guarantee that state won't change between the time the lock is released and the time that the value is
	 * returned.
	 *
	 * @param <V>   the type of value returned by the task
	 * @param task  the task to run while holding the lock
	 * @param stamp the optimistic read-lock to use
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if any checked exceptions are thrown
	 */
	private <V> V runWithOptimisticRead(Callable<V> task, long stamp)
	{
		if (stamp != 0)
		{
			V result = runTask(task);
			if (lock.validate(stamp))
				return result;
		}
		return read(task);
	}

	/**
	 * Acquires a read-lock, unless the caller already holds a read or write-lock. If the caller already holds a
	 * lock, no lock is acquired or released.
	 *
	 * @return a read-lock as a resource
	 */
	public CloseableLock read()
	{
		Long existingStamp = this.stamp.get();
		if (existingStamp != null && StampedLock.isLockStamp(existingStamp))
		{
			// Pre-existing read-lock or write-lock
			return ReentrantStampedLock::doNotUnlock;
		}
		// No lock, or optimistic read-lock
		long stamp = lock.readLock();
		this.stamp.set(stamp);
		return () ->
		{
			// Must re-read stamp value from ThreadLocal because tasks that invoke writeLock() may modify the
			// stamp value
			lock.unlockRead(this.stamp.get());
			this.stamp.set(existingStamp);
		};
	}

	/**
	 * Runs a task while holding a read-lock. If the caller already holds a read or write-lock, no lock is
	 * acquired or released.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	public <V> V read(Callable<V> task)
	{
		try (CloseableLock ignored = read())
		{
			return runTask(task);
		}
	}

	/**
	 * Runs a task while holding a read-lock. If the caller already holds a read or write-lock, no lock is
	 * acquired or released.
	 *
	 * @param task the task to run while holding the lock
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	public void read(Task task)
	{
		read(() ->
		{
			task.run();
			return null;
		});
	}

	/**
	 * Acquires a write-lock, unless the caller already holds one. If the caller already holds a lock, no lock
	 * is acquired or released.
	 * <p>
	 * If the caller holds a read-lock, an attempt is made to convert it into a write-lock. Conversions are not
	 * guaranteed to be atomic; consequently, there is no guarantee that state won't change between the time the
	 * read-lock is released and the write-lock is acquired. Callers must repeat any state checks to ensure that
	 * they still hold.
	 *
	 * @return a write-lock as a resource
	 */
	public CloseableLock write()
	{
		Long existingStamp = this.stamp.get();
		if (existingStamp == null)
			return noLockToWriteLock(null);
		if (StampedLock.isWriteLockStamp(existingStamp))
			return ReentrantStampedLock::doNotUnlock;
		if (!StampedLock.isReadLockStamp(existingStamp))
		{
			// optimistic read-lock
			return noLockToWriteLock(existingStamp);
		}
		// read-lock
		long writeLock = lock.tryConvertToWriteLock(existingStamp);
		if (writeLock == 0)
		{
			lock.unlockRead(existingStamp);
			writeLock = lock.writeLock();
		}
		this.stamp.set(writeLock);
		long finalWriteLock = writeLock;
		return () ->
		{
			// The returned stamp value is guaranteed to be a read-lock, but the stamp value may change.
			long readStamp = lock.tryConvertToReadLock(finalWriteLock);
			assert (readStamp != 0);
			this.stamp.set(readStamp);
		};
	}

	/**
	 * Acquires a write-lock with no pre-existing lock.
	 *
	 * @param existingStamp the existing stamp
	 * @return a write-lock as a resource
	 */
	private CloseableLock noLockToWriteLock(Long existingStamp)
	{
		this.stamp.set(lock.writeLock());
		return () ->
		{
			lock.unlock(this.stamp.get());
			this.stamp.set(existingStamp);
		};
	}

	/**
	 * Runs a task while holding a write-lock. If the caller already holds a lock, no lock is acquired or
	 * released.
	 * <p>
	 * If the caller holds a read-lock, an attempt is made to convert it into a write-lock. Conversions are not
	 * guaranteed to be atomic; consequently, there is no guarantee that state won't change between the time the
	 * read-lock is released and the write-lock is acquired. Callers must repeat any state checks to ensure that
	 * they still hold.
	 *
	 * @param <V>  the type of value returned by the task
	 * @param task the task to run while holding the lock
	 * @return the value returned by the task
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	public <V> V write(Callable<V> task)
	{
		try (CloseableLock ignored = write())
		{
			return runTask(task);
		}
	}

	/**
	 * Runs a task while holding a write-lock. If the caller already holds a lock, no lock is acquired or
	 * released.
	 * <p>
	 * If the caller holds a read-lock, an attempt is made to convert it into a write-lock. Conversions are not
	 * guaranteed to be atomic; consequently, there is no guarantee that state won't change between the time the
	 * read-lock is released and the write-lock is acquired. Callers must repeat any state checks to ensure that
	 * they still hold.
	 *
	 * @param task the task to run while holding the lock
	 * @throws NullPointerException    if {@code task} is null
	 * @throws WrappedCheckedException if {@code task} throws a checked exception
	 */
	public void write(Task task)
	{
		write(() ->
		{
			task.run();
			return null;
		});
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
	private <V> V runTask(Callable<V> task)
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