/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal;

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
	 * Uses {@link Comparable#compareTo(Object)} to determine the equality of non-null objects. If either
	 * argument is null, the equality operator {@code ==} is used instead.
	 */
	COMPARABLE
}