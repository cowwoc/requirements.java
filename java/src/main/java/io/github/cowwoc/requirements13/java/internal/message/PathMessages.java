/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.util.Difference;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractObjectValidator;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidator;
import io.github.cowwoc.requirements13.java.internal.validator.PathValidatorImpl;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

import static io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder.quoteName;

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
	public static MessageBuilder isExecutable(AbstractValidator<?, Path> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must exist, and the JVM must have sufficient privileges to execute it.");
		Path value = validator.getValueOrDefault(null);
		if (value != null)
		{
			messageBuilder.withContext(value.toAbsolutePath(), name);
			messageBuilder.withContext(Files.exists(value), name + ".exists()");
			messageBuilder.withContext(Files.isReadable(value), name + ".isReadable()");
			messageBuilder.withContext(Files.isWritable(value), name + ".isWritable()");
			messageBuilder.withContext(Files.isExecutable(value), name + ".isExecutable()");
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
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		return ObjectMessages.isEmptyFailed(validator);
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		return ObjectMessages.isNotEmptyFailed(validator);
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected path ({@code null} if undefined)
	 * @param expected     the expected path
	 * @return a message for the validation failure
	 */
	public static MessageBuilder contains(PathValidatorImpl validator, String expectedName, Path expected)
	{
		// "actual" must contain "expected".
		// actual  : 5
		// expected: 2
		return containsFailedImpl(validator, "must contain", expectedName, expected);
	}

	/**
	 * @param validator    the validator
	 * @param relationship the relationship between the actual and other value (e.g. "must contain")
	 * @param otherName    the name of the other value ({@code null} if undefined)
	 * @param other        the other value
	 * @return a message for the validation failure
	 */
	private static MessageBuilder containsFailedImpl(AbstractObjectValidator<?, ?> validator,
		String relationship, String otherName, Object other)
	{
		// "actual" must contain "other".
		// actual: /users
		// other : /users/root
		String name = validator.getName();
		String otherNameOrValue = validator.getNameOrValue("", otherName, "", other);
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " " + relationship + " " + otherNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (otherName != null)
			messageBuilder.withContext(other, otherName);
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted path ({@code null} if undefined)
	 * @param unwanted     the unwanted path
	 * @return a message for the validation failure
	 */
	public static Object doesNotContain(PathValidatorImpl validator, String unwantedName, Path unwanted)
	{
		// "actual" may not contain "unwanted".
		// actual  : /users
		// unwanted: /home/root
		return containsFailedImpl(validator, "may not contain", unwantedName, unwanted);
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected collection ({@code null} if undefined)
	 * @param expected     the collection of expected values
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsAnyFailed(AbstractObjectValidator<?, ?> validator, String expectedName,
		Collection<Path> expected)
	{
		// "actual" must contain at least one of the paths present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "", expected);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must contain at least one of the paths present in " +
				expectedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		return messageBuilder;
	}

	/**
	 * @param validator       the validator
	 * @param unwantedMatches the unwanted paths that matched ({@code null} if undefined)
	 * @param unwantedName    the name of the unwanted paths ({@code null} if undefined)
	 * @param unwanted        the collection of unwanted paths
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainAnyFailed(AbstractObjectValidator<?, ?> validator,
		Set<Path> unwantedMatches, String unwantedName, Collection<Path> unwanted)
	{
		// "actual" may not contain any of the paths paths in "unwanted".
		// actual         : [1, 2, 3]
		// unwanted       : [2, 3, 4]
		// unwantedMatches: [2, 3, 4]
		String unwantedNameOrValue = validator.getNameOrValue("", unwantedName, "", unwanted);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain any of the paths paths in " + unwantedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		if (unwantedMatches != null)
			messageBuilder.withContext(unwantedMatches, "unwantedMatches");
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param difference   the difference between the actual and expected values ({@code null} if undefined)
	 * @param expectedName the name of the paths ({@code null} if undefined)
	 * @param expected     the paths
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsExactlyFailed(AbstractObjectValidator<?, ?> validator,
		Difference<Path> difference, String expectedName, Object expected)
	{
		// "actual" must consist of the paths [/home/root, /home/guest, /home/banned].
		//
		// or
		//
		// "actual" must consist of the same paths as "expected".
		// actual  : [/home/root, /home/unwanted-guest, /home/banned]
		// expected: [/home/root, /home/guest, /home/banned]
		// missing : [/home/guest]
		// unwanted: [/home/unwanted-guest]

		String name = validator.getName();
		StringBuilder message = new StringBuilder(quoteName(name)).append(" must consist of the ");
		if (expectedName != null)
			message.append("same ");
		message.append("paths ");

		String expectedNameOrValue = validator.getNameOrValue("as ", expectedName, "", expected);
		message.append(expectedNameOrValue).append('.');

		MessageBuilder messageBuilder = new MessageBuilder(validator, message.toString());
		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		if (difference != null)
			messageBuilder.withContext(difference.onlyInOther(), "missing").
				withContext(difference.onlyInActual(), "unwanted");
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the paths ({@code null} if undefined)
	 * @param unwanted     the paths
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainExactlyFailed(AbstractObjectValidator<?, ?> validator,
		String unwantedName, Collection<Path> unwanted)
	{
		// "actual" may not consist of the paths [/home/root, /home/guest, /home/banned].
		//
		// or
		//
		// "actual" may not consist of the same paths as "expected".
		// unwanted  : [/home/guest]
		StringBuilder message = new StringBuilder(quoteName(validator.getName())).
			append(" may not consist of the ");
		if (unwantedName != null)
			message.append("same ");
		message.append("paths ");
		String unwantedNameOrValue = validator.getNameOrValue("as ", unwantedName, "", unwanted);
		message.append(unwantedNameOrValue).append('.');

		MessageBuilder messageBuilder = new MessageBuilder(validator, message.toString());
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		return messageBuilder;
	}

	/**
	 * @param validator      the validator
	 * @param missingMatches the expected paths that did not match ({@code null} if undefined)
	 * @param expectedName   the name of the expected paths ({@code null} if undefined)
	 * @param expected       the paths of expected values
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsAllFailed(AbstractObjectValidator<?, ?> validator,
		Collection<Path> missingMatches, String expectedName, Collection<Path> expected)
	{
		// "actual" must contain all the paths present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		// missing : [4]
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "", expected);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must contain all the paths present in " + expectedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		if (missingMatches != null)
			messageBuilder.withContext(missingMatches, "missing");
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted paths ({@code null} if undefined)
	 * @param unwanted     the collection of unwanted paths
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainAllFailed(AbstractObjectValidator<?, ?> validator,
		String unwantedName, Collection<Path> unwanted)
	{
		// "actual" may contain some, but not all, the paths present in "unwanted".
		// actual  : [1, 2, 3]
		// unwanted: [2, 3, 4]
		String unwantedNameOrValue = validator.getNameOrValue("", unwantedName, "", unwanted);
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may contain some, but not all, the paths present in " +
				unwantedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		return messageBuilder;
	}
}