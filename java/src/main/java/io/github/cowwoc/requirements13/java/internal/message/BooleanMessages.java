/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidator;

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