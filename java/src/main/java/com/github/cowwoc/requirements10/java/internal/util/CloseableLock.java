package com.github.cowwoc.requirements10.java.internal.util;

/**
 * Let the compiler know that releasing a lock does not throw any checked exceptions.
 */
@FunctionalInterface
public interface CloseableLock extends AutoCloseable
{
	@Override
	void close();
}