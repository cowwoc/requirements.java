/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

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
	public static MessageBuilder isTrue(AbstractValidator<?, Boolean> validator)
	{
		return Messages.constraint(validator, "must be true");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isFalse(AbstractValidator<?, Boolean> validator)
	{
		return Messages.constraint(validator, "must be false");
	}
}