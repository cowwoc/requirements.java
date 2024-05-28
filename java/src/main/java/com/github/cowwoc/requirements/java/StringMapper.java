package com.github.cowwoc.requirements.java;

/**
 * Returns the String representation of an object.
 */
@FunctionalInterface
public interface StringMapper
{
	/**
	 * Returns the String representation of an object.
	 *
	 * @param object an object
	 * @return the String representation of the object
	 */
	String apply(Object object);
}