package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for validators.
 */
public final class Messages
{
	private Messages()
	{
	}

	/**
	 * @param <T>        the type of the value
	 * @param validator  the validator
	 * @param constraint the constraint that the value must adhere to (e.g. "must be negative")
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder constraint(AbstractValidator<?, T> validator, String constraint)
	{
		// "actual" must be negative.
		// actual: 5
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " " + constraint + ".");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}
}