/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.jackson.internal.message;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements12.java.internal.validator.AbstractValidator;

import static io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for Multimap.
 */
public final class JsonNodeMessages
{
	private JsonNodeMessages()
	{
	}

	/**
	 * @param validator the validator
	 * @param name      the name of a property
	 * @return a message for the validation failure
	 */
	public static MessageBuilder property(AbstractValidator<?, ? extends JsonNode> validator, String name)
	{
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(validator.getName()) + " must contain a property named " + quoteName(name) + ".");
		JsonNode value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param type      a description of the expected type (e.g. "a string")
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isType(AbstractValidator<?, ? extends JsonNode> validator, String type)
	{
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(validator.getName()) + " must contain " + type + ".");
		JsonNode value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, "actual");
		return messageBuilder;
	}
}