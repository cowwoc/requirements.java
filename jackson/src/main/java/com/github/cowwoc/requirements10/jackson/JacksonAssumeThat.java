package com.github.cowwoc.requirements10.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;

import java.util.function.Function;

/**
 * Creates validators for the Jackson API, throwing an exception if a failure occurs.
 */
public interface JacksonAssumeThat
{
	/**
	 * Validates the state of a {@code JsonNode}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	<T extends JsonNode> JsonNodeValidator<T> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code JsonNode}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends JsonNode> JsonNodeValidator<T> assumeThat(T value);
}