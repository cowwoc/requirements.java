/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Validates the requirements of an {@link Object}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public interface ExtensibleObjectValidator<S, T>
{
	/**
	 * Returns the actual value.
	 *
	 * @return {@code Optional.empty()} if the actual value is not available (e.g. the value was converted to
	 * an incompatible type)
	 */
	Optional<T> getActual();

	/**
	 * Returns the list of failed validations.
	 *
	 * @return the list of failed validations
	 */
	List<ValidationFailure> getFailures();

//	/**
//	 * If any validations failed, throws an exception based on the first failure; otherwise, does nothing.
//	 *
//	 * @param type the type of exception to throw
//	 * @return this
//	 * @throws NullPointerException     if the actual value was {@code null} when it was not supposed to be
//	 * @throws IllegalArgumentException if any other validation failed
//	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output
//	 * format</a>
//	 * @see #getFailures()
//	 */
//	<E extends Exception> S throwOnFailure(Class<E> type) throws E;

	/**
	 * Ensures that the actual value is equal to an expected value.
	 *
	 * @param expected the expected value
	 * @return this
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the actual value is equal to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the actual value is not equal to another value.
	 *
	 * @param other the value to compare to
	 * @return this
	 */
	S isNotEqualTo(Object other);

	/**
	 * Ensures that the actual value is not equal to another variable.
	 *
	 * @param other the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 */
	S isNotEqualTo(Object other, String name);

	/**
	 * Ensures that the actual and expected objects are one and the same object.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return this
	 */
	S isSameObjectAs(Object expected, String name);

	/**
	 * Ensures that the actual value does not reference a specific object.
	 *
	 * @param other the object to compare to
	 * @param name  the name of the other object
	 * @return this
	 */
	S isNotSameObjectAs(Object other, String name);

	/**
	 * Ensures that the actual value is one of the elements in a collection. This is typically used to ensure
	 * that the actual value is valid.
	 *
	 * @param collection a collection
	 * @return this
	 */
	S isOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is not one of the elements in a collection. This is typically used to
	 * ensure that the actual value is not a reserved value.
	 *
	 * @param collection a collection
	 * @return this
	 */
	S isNotOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 */
	S isInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is not an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 */
	S isNotInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is null.
	 *
	 * @return this
	 */
	S isNull();

	/**
	 * Ensures that the actual value is not null.
	 *
	 * @return this
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
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the String representation of the actual value
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S asString(Consumer<StringValidator> consumer);
}
