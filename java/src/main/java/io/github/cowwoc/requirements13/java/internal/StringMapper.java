/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal;

import java.util.Set;

/**
 * Returns the String representation of an object.
 */
@FunctionalInterface
public interface StringMapper
{
	/**
	 * Returns the String representation of a value.
	 *
	 * @param value a value
	 * @param seen  the objects that we've seen before
	 * @return the String representation of the value
	 */
	String apply(Object value, Set<Object> seen);
}