package com.github.cowwoc.requirements11.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements11.jackson.validator.JsonNodeValidator;

/**
 * Creates validators for the Jackson API that throw {@code AssertionError} immediately on validation
 * failure.
 */
public interface JacksonAssertThat
{
	/**
	 * Validates the state of a {@code JsonNode}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	<T extends JsonNode> JsonNodeValidator<T> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code JsonNode}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends JsonNode> JsonNodeValidator<T> assertThat(T value);
}