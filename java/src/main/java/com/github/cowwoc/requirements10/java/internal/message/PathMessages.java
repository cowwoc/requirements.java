package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;
import com.github.cowwoc.requirements10.java.internal.validator.PathValidatorImpl;

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
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder exists(AbstractValidator<?, Path> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must exist.");
		Path value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
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
		Path value = validator.getValueOrDefault(null);
		if (value != null)
		{
			messageBuilder.withContext(value.toAbsolutePath(), name).
				withContext(options, "options");
		}
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
		Path value = validator.getValueOrDefault(null);
		if (value != null)
		{
			messageBuilder.withContext(value.toAbsolutePath(), name).
				withContext(options, "options");
		}
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isRelative(AbstractValidator<?, Path> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must reference a relative path.");
		Path value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value.toAbsolutePath(), name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isAbsolute(AbstractValidator<?, Path> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must reference an absolute path.");
		Path value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value.toAbsolutePath(), name);
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected path ({@code null} if undefined)
	 * @param expected     the expected path
	 * @return a message for the validation failure
	 */
	public static MessageBuilder contains(PathValidatorImpl validator, String expectedName, Path expected)
	{
		String actualName = validator.getName();

		//     "actual" must contain "expected".
		//     actual  : /users
		//     expected: /home/root
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "", expected);

		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(actualName) + " must contain " + expectedNameOrValue + ".");

		Object value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, actualName);
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		return messageBuilder;
	}
}