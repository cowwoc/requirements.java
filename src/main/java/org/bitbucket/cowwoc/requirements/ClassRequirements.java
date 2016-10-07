/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Verifies requirements of a {@link Class} parameter.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public interface ClassRequirements<T>
	extends ObjectRequirementsSpi<ClassRequirements<T>, Class<T>>, Isolatable<ClassRequirements<T>>
{
	/**
	 * Ensures that the parameter is a superclass or super-interface of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if {@code parameter} is not a supertype of {@code type}
	 */
	ClassRequirements<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException;
}
