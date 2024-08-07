/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for URIs.
 */
public final class UriMessages
{
	private UriMessages()
	{
	}

	/**
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder isAbsolute(AbstractValidator<?, ?> validator)
	{
		return Messages.constraint(validator, "must be an absolute URI");
	}

	/**
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder asUrl(AbstractValidator<?, ?> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be a valid URL.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder asUri(AbstractValidator<?, ?> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be a valid URI.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}
}