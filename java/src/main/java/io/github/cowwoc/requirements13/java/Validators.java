/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java;

import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;
import io.github.cowwoc.requirements13.annotation.CheckReturnValue;

import java.util.Map;
import java.util.Optional;

/**
 * A factory that creates different types of validators.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assert that()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for returning multiple validation failures.</li>
 * </ul>
 *
 * @param <S> the type of the validator factory
 */
public interface Validators<S>
{
	/**
	 * Returns a new factory instance with an independent configuration. This method is commonly used to inherit
	 * and update contextual information from the original factory before passing it into a nested operation.
	 * For example,
	 * {@snippet :
	 * JavaValidators copy = validators.copy();
	 * copy.getContext().put(json.toString(), "json");
	 * nestedOperation(copy);
	 *}
	 *
	 * @return a copy of this factory
	 */
	@CheckReturnValue
	S copy();

	/**
	 * Returns the contextual information inherited by validators created out by this factory. The contextual
	 * information is a map of key-value pairs that can provide more details about validation failures. For
	 * example, if the message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Note that values are wrapped in an {@code Optional} because modern maps do not support {@code null}
	 * values.
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 */
	Map<String, Optional<Object>> getContext();

	/**
	 * Sets the contextual information for validators created by this factory.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method may be overridden by
	 * {@link ValidatorComponent#withContext(Object, String)}}.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                  </ul>
	 */
	S withContext(Object value, String name);

	/**
	 * Removes the contextual information of validators created by this factory.
	 *
	 * @param name the parameter name
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                  </ul>
	 */
	S removeContext(String name);

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating this configuration affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	GlobalConfiguration globalConfiguration();
}