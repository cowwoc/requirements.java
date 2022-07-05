/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ObjectVerifier;
import com.github.cowwoc.requirements.java.StringVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a value that extends {@link Object} but the implementing verifier is not
 * guaranteed to be an {@link ObjectVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public interface ExtensibleObjectVerifier<S, T>
{
	/**
	 * Ensures that the actual value is equal to an expected value.
	 *
	 * @param expected the expected value
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not equal to value
	 * @see <a href="https://github.com/cowwoc/requirements/wiki/Textual_Diff.md">An explanation of the output
	 * format</a>
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the actual value is equal to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is not equal to {@code expected}. If
	 *                                  {@code name} is blank.
	 * @see <a href="https://github.com/cowwoc/requirements/wiki/Textual_Diff.md">An explanation of the output
	 * format</a>
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the actual value is not equal to another value.
	 *
	 * @param other the value to compare to
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is equal to {@code value}
	 */
	S isNotEqualTo(Object other);

	/**
	 * Ensures that the actual value is not equal to another variable.
	 *
	 * @param other the value to compare to
	 * @param name  the name of the variable
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is equal to {@code other}. If {@code name} is blank.
	 */
	S isNotEqualTo(Object other, String name);

	/**
	 * Ensures that the actual and expected objects are one and the same object.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code actual != expected}. If {@code name} is blank.
	 */
	S isSameObjectAs(Object expected, String name);

	/**
	 * Ensures that the actual value does not reference a specific object.
	 *
	 * @param other the object to compare to
	 * @param name  the name of the other object
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code actual == other}. If {@code name} is blank.
	 */
	S isNotSameObjectAs(Object other, String name);

	/**
	 * Ensures that the actual value is one of the elements in a collection. This is typically used to ensure
	 * that the actual value is valid.
	 *
	 * @param collection a collection
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain the actual value
	 */
	S isOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is not one of the elements in a collection. This is typically used to
	 * ensure that the actual value is not a reserved value.
	 *
	 * @param collection a collection
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} contains the actual value
	 */
	S isNotOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if the actual value is not an instance of {@code type}
	 */
	S isInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is not an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if the actual value is an instance of {@code type}
	 */
	S isNotInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is null.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the actual value is not null
	 */
	S isNull();

	/**
	 * Ensures that the actual value is not null.
	 *
	 * @return the updated verifier
	 * @throws NullPointerException if the actual value is null
	 */
	S isNotNull();

	/**
	 * Returns a verifier for the String representation of the actual value.
	 *
	 * @return a verifier for the String representation of the actual value
	 */
	StringVerifier asString();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the String representation of the actual value
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S asString(Consumer<StringVerifier> consumer);

	/**
	 * Indicates if the actual value is available.
	 *
	 * @return true unless {@link JavaRequirements#assertThat(Object, String) assertThat()} was invoked and
	 * assertions are disabled (in which case the value was discarded), or the actual value was converted to an
	 * incompatible type
	 */
	boolean isActualAvailable();

	/**
	 * Returns the actual value. The return value is undefined if {@link #isActualAvailable()}
	 * is {@code false}.
	 *
	 * @return the actual value
	 */
	T getActual();
}
