/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of a {@link Class} value.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <T> the type of the class
 */
public interface ClassValidator<T> extends ExtensibleObjectValidator<ClassValidator<T>, Class<T>>
{
	/**
	 * Ensures that the actual value is a superclass or super-interface of a class.
	 *
	 * @param type the class to compare to
	 * @return the updated validator
	 * @throws NullPointerException if {@code type} is null
	 */
	ClassValidator<T> isSupertypeOf(Class<?> type);
}
