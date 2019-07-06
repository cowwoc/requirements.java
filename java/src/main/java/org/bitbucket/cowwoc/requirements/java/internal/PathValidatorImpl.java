/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PathValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * Default implementation of {@code PathValidator}.
 */
public final class PathValidatorImpl extends AbstractObjectValidator<PathValidator, Path>
	implements PathValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public PathValidatorImpl(ApplicationScope scope, Configuration config, String name, Path actual,
	                         List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected PathValidator getThis()
	{
		return this;
	}

	@Override
	protected PathValidator getNoOp()
	{
		return new PathValidatorNoOp(scope, config, failures);
	}

	@Override
	public PathValidator exists()
	{
		if (!Files.exists(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " refers to a non-existent path.").
				addContext("Actual", actual.toAbsolutePath());
			failures.add(failure);
		}
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options)
	{
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
			if (!attrs.isRegularFile())
			{
				ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
					name + " must refer to a file.").
					addContext("Actual", actual.toAbsolutePath());
				failures.add(failure);
			}
		}
		catch (IOException e)
		{
			addValidationFailure(e);
		}
		return this;
	}

	/**
	 * Adds a validation failure corresponding to an I/O error.
	 *
	 * @param e the I/O error
	 */
	private void addValidationFailure(IOException e)
	{
		String message;
		Class<? extends Exception> type;
		if (e instanceof NoSuchFileException)
		{
			type = IllegalArgumentException.class;
			message = name + " refers to a non-existent path.";
		}
		else
		{
			type = e.getClass();
			message = "Failed to read attributes of " + name + ".";
		}
		ValidationFailure failure = new ValidationFailureImpl(this, type, message).
			setCause(e).
			addContext("Actual", actual.toAbsolutePath());
		failures.add(failure);
	}

	@Override
	public PathValidator isDirectory(LinkOption... options)
	{
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
			if (!attrs.isDirectory())
			{
				ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
					name + " must refer to a directory.").
					addContext("Actual", actual.toAbsolutePath());
				failures.add(failure);
			}
		}
		catch (IOException e)
		{
			addValidationFailure(e);
		}
		return this;
	}

	@Override
	public PathValidator isRelative()
	{
		if (actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must refer to a relative path.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		if (!actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must refer to an absolute path.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}
}
