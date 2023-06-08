/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Validates the requirements of an {@link Object}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public interface ExtensibleObjectValidator<S, T>
{
	/**
	 * Returns the actual value.
	 *
	 * @return the actual value
	 */
	@CheckReturnValue
	T getActual();

	/**
	 * Returns the list of failed validations. Modifying the returned list results in undefined behavior.
	 *
	 * @return the list of failed validations
	 */
	List<ValidationFailure> getFailures();

	/**
	 * Ensures that the actual value is equal to an expected value.
	 *
	 * @param expected the expected value
	 * @return the updated validator
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the actual value is equal to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the actual value is not equal to another value.
	 *
	 * @param other the value to compare to
	 * @return the updated validator
	 */
	S isNotEqualTo(Object other);

	/**
	 * Ensures that the actual value is not equal to another variable.
	 *
	 * @param other the value to compare to
	 * @param name  the name of {@code other}'s variable
	 * @return the updated validator
	 */
	S isNotEqualTo(Object other, String name);

	/**
	 * Ensures that the actual and expected objects are one and the same object.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return the updated validator
	 */
	S isSameObjectAs(Object expected, String name);

	/**
	 * Ensures that the actual value does not reference a specific object.
	 *
	 * @param other the object to compare to
	 * @param name  the name of the other object
	 * @return the updated validator
	 */
	S isNotSameObjectAs(Object other, String name);

	/**
	 * Ensures that the actual value is one of the elements in a collection. This is typically used to ensure
	 * that the actual value is valid.
	 *
	 * @param collection a collection
	 * @return the updated validator
	 */
	S isOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is not one of the elements in a collection. This is typically used to
	 * ensure that the actual value is not a reserved value.
	 *
	 * @param collection a collection
	 * @return the updated validator
	 */
	S isNotOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return the updated validator
	 */
	S isInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is not an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return the updated validator
	 */
	S isNotInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is null.
	 *
	 * @return the updated validator
	 */
	S isNull();

	/**
	 * Ensures that the actual value is not null.
	 *
	 * @return the updated validator
	 */
	S isNotNull();

	/**
	 * Returns a verifier for the String representation of the actual value.
	 *
	 * @return a verifier for the String representation of the actual value
	 */
	StringValidator asString();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the String representation of the actual value
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S asString(Consumer<StringValidator> consumer);
}