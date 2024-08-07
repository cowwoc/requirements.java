package com.github.cowwoc.requirements10.jackson.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements10.jackson.validator.JsonNodeValidator;

/**
 * Creates validators for the Jackson API that capture exceptions on validation failure rather than throwing
 * them immediately.
 */
public interface JacksonCheckIf
{
	/**
	 * Validates the state of a {@code JsonNode}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	<T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code JsonNode}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends JsonNode> JsonNodeValidator<T> checkIf(T value);
}