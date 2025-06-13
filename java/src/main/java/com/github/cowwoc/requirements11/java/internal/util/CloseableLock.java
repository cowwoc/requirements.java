package com.github.cowwoc.requirements11.java.internal.util;

/**
 * Let the compiler know that releasing a lock does not throw any checked exceptions.
 */
@FunctionalInterface
public interface CloseableLock extends AutoCloseable
{
	@Override
	void close();
}