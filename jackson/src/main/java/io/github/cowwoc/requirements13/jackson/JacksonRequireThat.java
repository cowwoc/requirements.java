/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.jackson;

import io.github.cowwoc.requirements13.jackson.validator.JsonNodeValidator;
import tools.jackson.databind.JsonNode;

/**
 * Creates validators for the Jackson API that throw exceptions immediately on validation failure.
 */
@FunctionalInterface
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