/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.internal.message;

import com.github.cowwoc.requirements11.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements11.java.internal.validator.AbstractValidator;

/**
 * Generates failure messages for booleans.
 */
public final class BooleanMessages
{
	private BooleanMessages()
	{
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isTrueFailed(AbstractValidator<?, Boolean> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be true");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isFalseFailed(AbstractValidator<?, Boolean> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be false");
	}
}