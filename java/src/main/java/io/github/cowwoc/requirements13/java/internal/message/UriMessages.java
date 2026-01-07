/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidator;

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
		return ValidatorMessages.constraintFailed(validator, "must be an absolute URI");
	}
}