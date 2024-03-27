package com.github.cowwoc.requirements.java;

/**
 * The method used to check whether two values are equal.
 */
public enum EqualityMethod
{
	/**
	 * Uses {@link Object#equals(Object)} to determine whether two objects are equal.
	 */
	OBJECT,
	/**
	 * Uses {@link Comparable#compareTo(Object)} to determine whether two objects are equal.
	 */
	COMPARABLE
}