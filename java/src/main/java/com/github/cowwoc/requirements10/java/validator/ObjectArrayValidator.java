/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ArrayComponent;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

/**
 * Validates the state of an {@code Object[]}.
 *
 * @param <T> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ObjectArrayValidator<T, E> extends
	ValidatorComponent<ObjectArrayValidator<T, E>, T>,
	ObjectComponent<ObjectArrayValidator<T, E>, T>,
	ArrayComponent<ObjectArrayValidator<T, E>, T, E>
{
	/**
	 * Ensures that the array contains only null values or only non-null values.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array contains a mix of null and non-null values
	 */
	ObjectArrayValidator<T, E> containsSameNullity();
}