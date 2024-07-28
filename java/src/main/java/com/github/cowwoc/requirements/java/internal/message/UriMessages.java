/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for URIs.
 */
public final class UriMessages
{
	private UriMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder isAbsolute(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		return Messages.constraint(scope, validator, "must be an absolute URI");
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder asUrl(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must be a valid URL.");
		validator.ifDefined(value -> messageBuilder.withContext(value, actualName));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message indicating that the validation failed
	 */
	public static MessageBuilder asUri(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must be a valid URI.");
		validator.ifDefined(value -> messageBuilder.withContext(value, actualName));
		return messageBuilder;
	}
}