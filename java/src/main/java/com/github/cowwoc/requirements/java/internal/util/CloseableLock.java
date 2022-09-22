package com.github.cowwoc.requirements.java.internal.util;

/**
 * Let the compiler know that releasing a lock does not throw any checked exceptions.
 */
public interface CloseableLock extends AutoCloseable
{
	@Override
	void close();
}