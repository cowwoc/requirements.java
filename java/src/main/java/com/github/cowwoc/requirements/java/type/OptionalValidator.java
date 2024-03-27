/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.Optional;
import java.util.function.Function;

/**
 * Validates the state of an {@link Optional} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of object in the optional
 */
public interface OptionalValidator<T> extends
	Validator<OptionalValidator<T>>,
	ObjectPart<OptionalValidator<T>, Optional<T>>
{
	/**
	 * Ensures that the optional contains a value.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional is empty
	 */
	OptionalValidator<T> isPresent();

	/**
	 * Ensures that the optional is absent.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional contains a value
	 */
	OptionalValidator<T> isEmpty();

	/**
	 * Ensures that the optional contains a value.
	 *
	 * @param expected the expected value; {@code null} matches an empty optional
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional does not contain {@code expected}
	 */
	OptionalValidator<T> contains(Object expected);

	/**
	 * Ensures that the optional contains {@code expected}.
	 *
	 * @param expected the expected value ({@code null} matches an empty optional)
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the optional does not contain {@code expected}.
	 */
	OptionalValidator<T> contains(Object expected, String name);
}