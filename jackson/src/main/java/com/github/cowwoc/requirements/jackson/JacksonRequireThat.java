package com.github.cowwoc.requirements.jackson;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Creates validators for the Jackson API, throwing an exception if a failure occurs.
 */
public interface JacksonRequireThat
{
	/**
	 * Validates the state of a {@code JsonNode}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T extends JsonNode> JsonNodeValidator<T> requireThat(T value, String name);
}