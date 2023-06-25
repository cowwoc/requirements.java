package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Generates failure messages for strings.
 */
public final class StringMessages
{
	private StringMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isBlank(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " must be empty or contain only white space codepoints.");
		if (value != null)
			message.addContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotBlank(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " may not be empty or contain only white space codepoints.");
		if (value != null)
			message.addContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isTrimmed(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " may not contain leading or trailing white space.");
		if (value != null)
			message.addContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isStripped(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " may not contain leading or trailing white space codepoints.");
		if (value != null)
			message.addContext(value, "Actual");
		return message;
	}
}