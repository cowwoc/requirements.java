/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.util;

/**
 * Let the compiler know that releasing a lock does not throw any checked exceptions.
 */
@FunctionalInterface
public interface CloseableLock extends AutoCloseable
{
	@Override
	void close();
}