
/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ArrayPart;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Validates the state of a {@code char[]} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveCharacterArrayValidator extends
	Validator<PrimitiveCharacterArrayValidator>,
	ObjectPart<PrimitiveCharacterArrayValidator, char[]>,
	ArrayPart<PrimitiveCharacterArrayValidator, Character, char[]>
{
	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	PrimitiveCharacterArrayValidator contains(char expected);

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain {@code expected}.
	 */
	PrimitiveCharacterArrayValidator contains(char expected, String name);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array contains {@code unwanted}
	 */
	PrimitiveCharacterArrayValidator doesNotContain(char unwanted);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @param name     the name of the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains {@code unwanted}.
	 */
	PrimitiveCharacterArrayValidator doesNotContain(char unwanted, String name);

	/**
	 * Ensures that the array is sorted by its natural ordering.
	 *
	 * @return this
	 * @throws NullPointerException if the value is null
	 * @see Comparator#naturalOrder()
	 */
	PrimitiveCharacterArrayValidator isSorted();

	/**
	 * Returns a validator for the boxed representation of the value.
	 *
	 * @return a validator for the boxed representation of the value
	 */
	ObjectArrayValidator<Character, Character[]> asBoxed();
}