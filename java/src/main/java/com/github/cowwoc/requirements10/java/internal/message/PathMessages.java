package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
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
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder exists(AbstractValidator<?, T> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must exist.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param type      the expected type of the path
	 * @param options   determines how symbolic links are handled
	 * @return a message for the validation failure
	 */
	public static MessageBuilder exists(AbstractValidator<?, Path> validator, String type,
		LinkOption... options)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must reference an existing " + type + ".");
		validator.ifDefined(value -> messageBuilder.withContext(value.toAbsolutePath(), name).
			withContext(options, "options"));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param type      the expected type of the path
	 * @param options   determines how symbolic links are handled
	 * @param cause     the underlying cause of the failure
	 * @return a message for the validation failure
	 */
	public static MessageBuilder readAttributes(AbstractValidator<?, Path> validator, String type,
		LinkOption[] options, Throwable cause)
	{
		String name = validator.getName();
		String message;
		if (cause instanceof NoSuchFileException)
			message = quoteName(name) + " referenced a non-existent " + type + ".";
		else
			message = "Failed to read attributes of " + quoteName(name) + ".";
		MessageBuilder messageBuilder = new MessageBuilder(validator, message);
		validator.ifDefined(value -> messageBuilder.withContext(value.toAbsolutePath(), name));
		messageBuilder.withContext(options, "options");
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isRelative(AbstractValidator<?, T> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must reference a relative path.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isAbsolute(AbstractValidator<?, T> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must reference an absolute path.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}
}