package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Generates failure messages for objects.
 */
public final class ObjectMessages
{
	private ObjectMessages()
	{
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfExpected the name of the expected object
	 * @param expected       the expected object
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isSameReferenceAs(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfExpected, Object expected)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"must be the same reference as", nameOfExpected, expected);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfUnwanted the name of the unwanted object
	 * @param unwanted       the unwanted object
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotSameReferenceAs(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfUnwanted, Object unwanted)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not be the same reference as", nameOfUnwanted, unwanted);
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            the value being validated
	 * @param valueIsAvailable true if the value may be used
	 * @param expected         the expected type
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isInstanceOf(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, boolean valueIsAvailable, Class<?> expected)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			nameOfValue + " must be an instance of " + expected.getName() + ".");
		if (valueIsAvailable)
		{
			if (value != null)
				message.addContext(value.getClass(), nameOfValue + ".getClass()");
			message.addContext(validator.configuration().stringMappers().toString(value), nameOfValue);
		}
		return message;
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param valueIsAvailable true if the value may be used
	 * @param unwanted         the unwanted type
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotInstanceOf(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, boolean valueIsAvailable, Class<?> unwanted)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			nameOfValue + " may not be an instance of " + unwanted.getName() + ".");

		if (valueIsAvailable)
		{
			String classOfValue;
			if (value == null)
				classOfValue = "null";
			else
				classOfValue = value.getClass().getName();
			message.addContext(classOfValue, nameOfValue + ".getClass()").
				addContext(value, "Actual");
		}
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNull(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value)
	{
		return new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be null.").
			addContext(value, "Actual");
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isNotNull(ApplicationScope scope, AbstractValidator<?> validator, String name)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " may not be null.");
	}
}