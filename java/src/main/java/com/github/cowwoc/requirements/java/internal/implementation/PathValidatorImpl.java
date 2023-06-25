/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.PathValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PathValidatorImpl extends AbstractObjectValidator<PathValidator, Path>
	implements PathValidator
{
	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PathValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator,
		String name, Path value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PathValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, Path value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private PathValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Path value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public PathValidator exists()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " references a non-existent path.").
					toString());
		}
		else if (value == null)
		{
			addIllegalArgumentException(ObjectMessages.isNotNull(scope, this, this.name).
				toString());
		}
		else if (!Files.exists(value))
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " references a non-existent path.").
					addContext(value, "Actual").
					toString());
		}
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options) throws IOException
	{
		scope.getInternalValidator().requireThat(options, "options").isNotNull();
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference a file.").
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			try
			{
				BasicFileAttributes attrs = Files.readAttributes(value, BasicFileAttributes.class, options);
				if (!attrs.isRegularFile())
				{
					addIllegalArgumentException(
						new MessageBuilder(scope, this, name + " must reference a file.").
							addContext(value.toAbsolutePath(), name).
							addContext(options, "options").
							toString());
				}
			}
			catch (IOException e)
			{
				handleIoException(e);
			}
		}
		return this;
	}

	@Override
	public PathValidator isDirectory(LinkOption... options) throws IOException
	{
		scope.getInternalValidator().requireThat(options, "options").isNotNull();
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference a directory.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else
		{
			try
			{
				BasicFileAttributes attrs = Files.readAttributes(value, BasicFileAttributes.class, options);
				if (!attrs.isDirectory())
				{
					addIllegalArgumentException(
						new MessageBuilder(scope, this, name + " must reference a directory.").
							addContext(value.toAbsolutePath(), name).
							addContext(options, "options").
							toString());
				}
			}
			catch (IOException e)
			{
				handleIoException(e);
			}
		}
		return this;
	}

	/**
	 * Adds a validation failure corresponding to an I/O error.
	 *
	 * @param e       the I/O error
	 * @param options options indicating how symbolic links are handled
	 */
	private void handleIoException(IOException e, LinkOption... options) throws IOException
	{
		if (e instanceof NoSuchFileException)
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " referenced a non-existent file.").
					addContext(value.toAbsolutePath(), name).
					addContext(options, "options").
					toString());
		}
		else
		{
			addFailure(new MessageBuilder(scope, this, "Failed to read attributes of " + name + ".").
				addContext(value.toAbsolutePath(), name).
				addContext(options, "options").
				toString(), e, IOException::new);
		}
	}

	@Override
	public PathValidator isRelative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference a relative path.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.isAbsolute())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference a relative path.").
					addContext(value.toAbsolutePath(), name).
					toString());
		}
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference an absolute path.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!value.isAbsolute())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must reference an absolute path.").
					addContext(value.toAbsolutePath(), name).
					toString());
		}
		return this;
	}
}