package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

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
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isBlank(ApplicationScope scope, AbstractValidator<?, String> validator)
	{
		String actualName = validator.getName();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must be empty or contain only whitespace codepoints.");
		validator.ifDefined(value -> message.withContext(value, actualName));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotBlank(ApplicationScope scope, AbstractValidator<?, String> validator)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			validator.getName() + " may not be empty or contain only whitespace codepoints.");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isTrimmed(ApplicationScope scope, AbstractValidator<?, String> validator)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not contain leading or trailing whitespace.");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isStripped(ApplicationScope scope, AbstractValidator<?, String> validator)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not contain leading or trailing whitespace codepoints.");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param prefix    the value that the string must start with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder startsWith(ApplicationScope scope, AbstractValidator<?, String> validator,
		String prefix)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must start with " + stringMappers.toString(prefix) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param prefix    the value that the string must start with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotStartWith(ApplicationScope scope,
		AbstractValidator<?, String> validator, String prefix)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not start with " + stringMappers.toString(prefix) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param suffix    the value that the string must end with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder endsWith(ApplicationScope scope, AbstractValidator<?, String> validator,
		String suffix)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must end with " + stringMappers.toString(suffix) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param suffix    the value that the string must end with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotEndWith(ApplicationScope scope, AbstractValidator<?, String> validator,
		String suffix)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not end with " + stringMappers.toString(suffix) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param expected  the expected value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder contains(ApplicationScope scope, AbstractValidator<?, String> validator,
		String expected)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must contain " + stringMappers.toString(expected) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param unwanted  the unwanted value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContain(ApplicationScope scope, AbstractValidator<?, String> validator,
		String unwanted)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not contain " + stringMappers.toString(unwanted) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param regex     the regular expression
	 * @return a message for the validation failure
	 */
	public static MessageBuilder matches(ApplicationScope scope, AbstractValidator<?, String> validator,
		String regex)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder message = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must match the regular expression " +
				stringMappers.toString(regex) + ".");
		validator.ifDefined(value -> message.withContext(value, "actual"));
		return message;
	}
}