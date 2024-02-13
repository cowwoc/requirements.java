package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

/**
 * Generates failure messages for comparable types.
 */
public final class ComparableMessages
{
	private ComparableMessages()
	{
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param nameOfLimit      the name of the value's bound
	 * @param maximumExclusive the exclusive upper bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isLessThan(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfLimit, Object maximumExclusive)
	{
		return getExpectedVsActual(scope, validator, nameOfValue, value, "must be less than", nameOfLimit,
			maximumExclusive);
	}

	/**
	 * @param validator   the validator
	 * @param nameOfValue the name of the value
	 * @param value       (optional) the value being validated
	 * @param operation   a description of what the value is expected to do (e.g. "must contain")
	 * @param nameOfOther (optional) the name of the "other" value
	 * @param other       an expected or unwanted value to compare against
	 * @return a message for the validation failure
	 */
	public static MessageBuilder getExpectedVsActual(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String operation, String nameOfOther, Object other)
	{
		StringBuilder message = new StringBuilder();
		message.append(MessageBuilder.quoteName(nameOfValue)).append(" ").append(operation).append(" ");
		if (nameOfOther == null)
			message.append(validator.configuration().stringMappers().toString(other));
		else
			message.append(MessageBuilder.quoteName(nameOfOther));
		message.append(".");

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, message.toString());
		if (EqualMessages.isMixedNullity(nameOfOther, value))
		{
			// If there is only one contextual parameter, name it "Actual".
			if (nameOfOther == null)
				nameOfValue = "Actual";
			else
				nameOfOther = "Actual";
		}
		if (nameOfOther != null)
			messageBuilder.addContext(other, nameOfOther);
		if (value != null)
			messageBuilder.addContext(value, nameOfValue);
		return messageBuilder;
	}

	/**
	 * @param validator   the validator
	 * @param nameOfValue the name of the value
	 * @param value       (optional) the value being validated
	 * @param operation   a description of what the value is expected to do (e.g. "must contain")
	 * @param nameOfOther (optional) the name of the "other" value
	 * @param other       an expected or unwanted value to compare against
	 * @param pluralizer  (optional) the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder getExpectedVsActual(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Integer value, String operation, String nameOfOther, int other, Pluralizer pluralizer)
	{
		StringBuilder message = new StringBuilder();
		message.append(MessageBuilder.quoteName(nameOfValue)).append(" ").append(operation).append(" ");
		if (nameOfOther == null)
			message.append(validator.configuration().stringMappers().toString(other));
		else
			message.append(MessageBuilder.quoteName(nameOfOther));
		if (pluralizer != null)
			message.append(" ").append(pluralizer.nameOf(other));
		message.append(".");

		MessageBuilder builder = new MessageBuilder(scope, validator, message.toString());
		if (nameOfOther != null)
			builder.addContext(other, nameOfOther);
		if (value != null)
			builder.addContext(value, nameOfValue);
		return builder;
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param nameOfLimit      the name of the value's bound
	 * @param maximumInclusive the inclusive upper bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isLessThanOrEqualTo(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfLimit, Object maximumInclusive)
	{
		return getExpectedVsActual(scope, validator, nameOfValue, value, "must be less than or equal to",
			nameOfLimit, maximumInclusive);
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param nameOfLimit      the name of the value's bound
	 * @param minimumInclusive the inclusive lower bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isGreaterThanOrEqualTo(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfLimit, Object minimumInclusive)
	{
		return getExpectedVsActual(scope, validator, nameOfValue, value, "must be greater than or equal to",
			nameOfLimit, minimumInclusive);
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param nameOfLimit      the name of the value's bound
	 * @param minimumExclusive the exclusive lower bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isGreaterThan(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfLimit, Object minimumExclusive)
	{
		return getExpectedVsActual(scope, validator, nameOfValue, value, "must be greater than",
			nameOfLimit, minimumExclusive);
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            (optional) the value being validated
	 * @param minimum          the Object representation of the inclusive lower limit
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the Object representation of the exclusive upper limit
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @param pluralizer       (optional) the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isBetween(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, Object minimum, boolean minimumInclusive, Object maximum,
		boolean maximumInclusive, Pluralizer pluralizer)
	{
		StringBuilder message = new StringBuilder().append(MessageBuilder.quoteName(nameOfValue));
		if (pluralizer == null)
			message.append(" must be between ");
		else
			message.append(" must contain ");
		if (minimumInclusive)
			message.append('[');
		else
			message.append('(');
		StringMappers stringMappers = validator.configuration().stringMappers();
		String minimumAsString = stringMappers.toString(minimum);
		String maximumAsString = stringMappers.toString(maximum);
		message.append(minimumAsString).append(", ").append(maximumAsString);
		if (maximumInclusive)
			message.append(']');
		else
			message.append(')');
		if (pluralizer != null)
			message.append(" ").append(pluralizer.nameOf(2));
		message.append(".");
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, message.toString());

		if (value != null)
			messageBuilder.addContext(value, "Actual");
		return messageBuilder;
	}
}