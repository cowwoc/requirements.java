package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for paths.
 */
public final class PathMessages
{
	private PathMessages()
	{
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder exists(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must exist.");
		validator.ifDefined(value -> messageBuilder.withContext(value, validator.getName()));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param type      the expected type of the path
	 * @param options   determines how symbolic links are handled
	 * @return a message for the validation failure
	 */
	public static MessageBuilder exists(ApplicationScope scope, AbstractValidator<?, Path> validator,
		String type, LinkOption... options)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must reference an existing " + type + ".");
		validator.ifDefined(value -> messageBuilder.withContext(value.toAbsolutePath(), validator.getName()).
			withContext(options, "options"));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param type      the expected type of the path
	 * @param options   determines how symbolic links are handled
	 * @param cause     the underlying cause of the failure
	 * @return a message for the validation failure
	 */
	public static MessageBuilder readAttributes(ApplicationScope scope, AbstractValidator<?, Path> validator,
		String type, LinkOption[] options, Throwable cause)
	{
		String name = validator.getName();
		String message;
		if (cause instanceof NoSuchFileException)
			message = quoteName(name) + " referenced a non-existent " + type + ".";
		else
			message = "Failed to read attributes of " + quoteName(name) + ".";
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, message);
		validator.ifDefined(value -> messageBuilder.withContext(value.toAbsolutePath(), name));
		messageBuilder.withContext(options, "options");
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isRelative(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must reference a relative path.");
		validator.ifDefined(value -> messageBuilder.withContext(value, validator.getName()));
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isAbsolute(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must reference an absolute path.");
		validator.ifDefined(value -> messageBuilder.withContext(value, validator.getName()));
		return messageBuilder;
	}
}