package com.github.cowwoc.requirements.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements.java.type.part.Validator;

/**
 * Creates validators for the Jackson API, recording any failures that occur.
 * <p>
 * A validation method may throw exceptions in three scenarios:
 * <ol>
 * <li>The method arguments are invalid, e.g. {@code isGreaterThan(value, null)}.</li>
 * <li>The method encounters a predictable but unavoidable failure that can be recovered from, e.g. an I/O
 * error.</li>
 * <li>The value fails the validation check, e.g. {@code isGreaterThan(5)} on a value of 0.</li>
 * </ol>
 * {@code requireThat()} throws an exception in all scenarios. {@code checkIf()} only throws exceptions in
 * scenarios 1 and 2. For scenario 3, the exception is available via
 * {@link Validator#elseGetException() validator.elseGetException()}}.
 */
public interface JacksonCheckIf
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
	<T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code JsonNode}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends JsonNode> JsonNodeValidator<T> checkIf(T value);
}