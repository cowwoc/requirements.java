package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

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
	 * @param scope      the application configuration
	 * @param validator  the validator
	 * @param constraint the constraint that the value must adhere to (e.g. "must be negative")
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder constraint(ApplicationScope scope, AbstractValidator<?, T> validator,
		String constraint)
	{
		// "actual" must be negative.
		// actual: 5
		String actualName = validator.getName();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(actualName) + " " + constraint + ".");
		validator.ifDefined(value -> message.withContext(value, actualName));
		return message;
	}
}