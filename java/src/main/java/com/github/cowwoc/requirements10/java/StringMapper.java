package com.github.cowwoc.requirements10.java;

import java.util.Objects;
import java.util.Set;

/**
 * Returns the String representation of an object.
 */
@FunctionalInterface
public interface StringMapper
{
	/**
	 * Uses {@link Objects#toString(Object)} to convert value to a string.
	 */
	StringMapper OBJECTS_TOSTRING = (value, seen) -> Objects.toString(value);

	/**
	 * Returns the String representation of a value.
	 *
	 * @param value a value
	 * @param seen  the objects that we've seen before
	 * @return the String representation of the value
	 */
	String apply(Object value, Set<Object> seen);
}