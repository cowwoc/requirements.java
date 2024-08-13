package com.github.cowwoc.requirements10.java.internal;

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