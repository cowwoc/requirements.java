/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator.component;

import io.github.cowwoc.requirements13.java.MultipleFailuresException;
import io.github.cowwoc.requirements13.java.ValidationFailures;
import io.github.cowwoc.requirements13.java.Validators;
import io.github.cowwoc.requirements13.annotation.CheckReturnValue;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Methods that all validators must contain.
 *
 * @param <S> the type of validator
 * @param <T> the type of the value that is being validated
 */
public interface ValidatorComponent<S, T>
{
	/**
	 * Returns the name of the value.
	 *
	 * @return the name of the value
	 */
	String getName();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value to use if the value is invalid
	 * @return the value, or {@code defaultValue} if the value is invalid (e.g. due to dereferencing a property
	 * of a {@code null} object)
	 */
	@CheckReturnValue
	T getValueOrDefault(T defaultValue);

	/**
	 * Returns the contextual information for upcoming validations carried out by this validator. The contextual
	 * information is a map of key-value pairs that can provide more details about validation failures. For
	 * example, if the message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Note that values are wrapped in an {@code Optional} because modern maps do not support {@code null}
	 * values.
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 * @see Validators#getContext()
	 */
	Map<String, Optional<Object>> getContext();

	/**
	 * Sets the contextual information for upcoming validations.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method override any values that are set using
	 * {@link Validators#withContext(Object, String)}.
	 * <p>
	 * There is no way to remove contextual information from a validator. Thread-level contextual information is
	 * removed automatically.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return this
	 * @throws NullPointerException     if {@code name} is {@code null}
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	S withContext(Object value, String name);

	/**
	 * Facilitates the validation of related properties. For example:
	 * <p>
	 * {@snippet :
	 * requireThat(nameToFrequency, "nameToFrequency").
	 *   and(m -> m.size().isPositive()).
	 *   and(m -> m.keySet().contains("John"));
	 *}
	 * <p>
	 * Any changes made during the validation process will impact this validator.
	 *
	 * @param validation the nested validation
	 * @return this
	 * @throws NullPointerException if {@code validation} is {@code null}
	 */
	S and(Consumer<? super S> validation);

	/**
	 * Checks if any validation has failed.
	 *
	 * @return {@code true} if at least one validation has failed
	 */
	boolean validationFailed();

	/**
	 * Returns the list of failed validations.
	 *
	 * @return an unmodifiable list of failed validations
	 */
	@CheckReturnValue
	ValidationFailures elseGetFailures();

	/**
	 * Throws an exception if a validation failed; otherwise, returns {@code true}.
	 *
	 * @return true if the validation passed
	 * @throws RuntimeException          if a method precondition was violated
	 * @throws Error                     if a class invariant or method postcondition was violated
	 * @throws MultipleFailuresException if more than one validation failed. This exception contains a list of
	 *                                   the failures.
	 */
	boolean elseThrow();

	/**
	 * Returns the contextual information associated with this validator.
	 *
	 * @return the contextual information associated with this validator
	 */
	String getContextAsString();
}