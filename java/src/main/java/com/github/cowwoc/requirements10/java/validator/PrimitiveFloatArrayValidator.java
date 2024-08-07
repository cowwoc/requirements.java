/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ArrayComponent;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.Comparator;

/**
 * Validates the state of a {@code float[]}.
 */
public interface PrimitiveFloatArrayValidator extends
	ValidatorComponent<PrimitiveFloatArrayValidator, float[]>,
	ObjectComponent<PrimitiveFloatArrayValidator, float[]>,
	ArrayComponent<PrimitiveFloatArrayValidator, float[], Float>
{
	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	PrimitiveFloatArrayValidator contains(float expected);

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the array does not contain {@code expected}</li>
	 *                                  </ul>
	 */
	PrimitiveFloatArrayValidator contains(float expected, String name);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array contains {@code unwanted}
	 */
	PrimitiveFloatArrayValidator doesNotContain(float unwanted);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @param name     the name of the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the array contains {@code unwanted}</li>
	 *                                  </ul>
	 */
	PrimitiveFloatArrayValidator doesNotContain(float unwanted, String name);

	/**
	 * Ensures that the array is sorted by its natural ordering.
	 *
	 * @return this
	 * @throws NullPointerException if the value is null
	 * @see Comparator#naturalOrder()
	 */
	PrimitiveFloatArrayValidator isSorted();
}