package com.github.cowwoc.requirements10.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements10.jackson.validator.JsonNodeValidator;

/**
 * Creates validators for the Jackson API that throw exceptions immediately on validation failure.
 */
public interface JacksonRequireThat
{
	/**
	 * Validates the state of a {@code JsonNode}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	<T extends JsonNode> JsonNodeValidator<T> requireThat(T value, String name);
}