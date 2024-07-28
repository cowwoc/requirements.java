/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.jackson.internal.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for Multimap.
 */
public final class JsonNodeMessages
{
	private JsonNodeMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of a property
	 * @return a message for the validation failure
	 */
	public static MessageBuilder property(ApplicationScope scope,
		ValidatorComponent<?, ? extends JsonNode> validator, MaybeUndefined<? extends JsonNode> value,
		String name)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must contain a property named " + quoteName(name) + ".");
		value.ifDefined(v -> messageBuilder.withContext(v, name));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param type      a description of the expected type (e.g. "a string")
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isType(ApplicationScope scope,
		ValidatorComponent<?, ? extends JsonNode> validator, MaybeUndefined<? extends JsonNode> value,
		String type)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must contain " + type + ".");
		value.ifDefined(v -> messageBuilder.withContext(v, "actual"));
		return messageBuilder;
	}
}