/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.PathValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * Creates a new PathValidatorImpl with existing validation failures.
	 *
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public PathValidatorImpl(ApplicationScope scope, Configuration config, String name, Path actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected PathValidator getThis()
	{
		return this;
	}

	@Override
	public PathValidator exists()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!Files.exists(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " refers to a non-existent path.").
				addContext("Actual", actual.toAbsolutePath());
			addFailure(failure);
		}
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options)
	{
		if (fatalFailure)
			return this;
		JavaRequirements internalVerifier = scope.getInternalVerifier();
		internalVerifier.requireThat(options, "options").isNotNull();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
			if (!attrs.isRegularFile())
			{
				ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
					name + " must refer to a file.").
					addContext("Actual", actual.toAbsolutePath());
				addFailure(failure);
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
		ValidationFailure failure = new ValidationFailureImpl(scope, config, type, message).
			setCause(e).
			addContext("Actual", actual.toAbsolutePath());
		addFailure(failure);
	}

	@Override
	public PathValidator isDirectory(LinkOption... options)
	{
		if (fatalFailure)
			return this;
		JavaRequirements internalVerifier = scope.getInternalVerifier();
		internalVerifier.requireThat(options, "options").isNotNull();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
			if (!attrs.isDirectory())
			{
				ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
					name + " must refer to a directory.").
					addContext("Actual", actual.toAbsolutePath());
				addFailure(failure);
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
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must refer to a relative path.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		if (fatalFailure)
			return this;
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must refer to an absolute path.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}
}
