package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Generates failure messages for numbers.
 */
public final class NumberMessages
{
	private NumberMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNegative(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be negative.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotNegative(ApplicationScope scope, AbstractValidator<?> validator,
		String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " may not be negative.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isZero(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be zero.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotZero(ApplicationScope scope, AbstractValidator<?> validator, String name)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " may not be zero.");
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isPositive(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be positive.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotPositive(ApplicationScope scope, AbstractValidator<?> validator,
		String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " may not be positive.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param nameOfValue  the name of the value
	 * @param value        (optional) the value being validated
	 * @param nameOfFactor (optional) the name of the factor
	 * @param factor       the value being multiplied by
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isMultipleOf(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfFactor, Object factor)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"must be less than", nameOfFactor, factor);
	}

	/**
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param nameOfValue  the name of the value
	 * @param value        (optional) the value being validated
	 * @param nameOfFactor (optional) the name of the factor
	 * @param factor       the value being multiplied by
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotMultipleOf(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfFactor, Object factor)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not be a multiple of", nameOfFactor, factor);
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isWholeNumber(ApplicationScope scope, AbstractValidator<?> validator,
		String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be a whole number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotWholeNumber(ApplicationScope scope, AbstractValidator<?> validator,
		String name, Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " may not be a whole number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNumber(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " must be a well-defined number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotNumber(ApplicationScope scope, AbstractValidator<?> validator,
		String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			name + " may not be a well-defined number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isFinite(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be a finite number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isInfinite(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be an infinite number.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}
}