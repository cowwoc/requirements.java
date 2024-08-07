package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

/**
 * Generates failure messages for classes.
 */
public final class ClassMessages
{
	private ClassMessages()
	{
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isPrimitive(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be a primitive type");
	}

	/**
	 * @param <U>       the type to compare to
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <U, T> MessageBuilder isSupertypeOf(AbstractValidator<?, T> validator,
		GenericType<? extends U> type)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			MessageBuilder.quoteName(name) + " must be a supertype of " + type + ".");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param <U>       the type to compare to
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <U, T> MessageBuilder isSubtypeOf(AbstractValidator<?, T> validator,
		GenericType<? super U> type)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			MessageBuilder.quoteName(name) + " must be a subtype of " + type + ".");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}
}