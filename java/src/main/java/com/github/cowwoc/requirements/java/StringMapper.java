package com.github.cowwoc.requirements.java;

/**
 * Returns a String representation of an object.
 */
@FunctionalInterface
public interface StringMapper
{
	/**
	 * Returns the String representation of an object.
	 *
	 * @param object an object
	 * @return a String representation of the object
	 */
	String apply(Object object);
}