/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.PathMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.PathValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class PathValidatorImpl extends AbstractObjectValidator<PathValidator, Path>
	implements PathValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public PathValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Path> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public PathValidator exists()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Files::exists))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				PathMessages.exists(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options) throws IOException
	{
		isType("file", BasicFileAttributes::isRegularFile, options);
		return this;
	}

	/**
	 * Validates the type of a path.
	 *
	 * @param type                       the expected type of the path
	 * @param attributesMatchExpectation returns {@code true} if the path matches the expected type
	 * @param options                    determines how symbolic links are handled
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a file
	 * @throws IOException              if an I/O error occurs while reading the file attributes (e.g., the file
	 *                                  does not exist or the user lacks the required permissions)
	 */
	private void isType(String type, Function<BasicFileAttributes, Boolean> attributesMatchExpectation,
		LinkOption... options) throws IOException
	{
		scope.getInternalValidators().requireThat(options, "options").isNotNull();
		if (value.isNull())
			onNull();
		try
		{
			switch (value.test(value -> isType(value, attributesMatchExpectation, options)))
			{
				case UNDEFINED, FALSE -> addIllegalArgumentException(
					PathMessages.exists(this, type, options).toString());
			}
		}
		catch (WrappedCheckedException e)
		{
			Throwable cause = e.getCause();
			if (!(cause instanceof IOException ioe))
				throw e;
			addIOException(
				PathMessages.readAttributes(this, type, options, cause).toString(), ioe);
		}
	}

	/**
	 * Validates the type of a path.
	 *
	 * @param path                       the path
	 * @param attributesMatchExpectation returns {@code true} if the path matches the expected type
	 * @param options                    determines how symbolic links are handled
	 * @return {@code true} if the path matched the expected type
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a file
	 */
	private boolean isType(Path path, Function<BasicFileAttributes, Boolean> attributesMatchExpectation,
		LinkOption... options)
	{
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class, options);
			return attributesMatchExpectation.apply(attrs);
		}
		catch (IOException e)
		{
			throw WrappedCheckedException.wrap(e);
		}
	}

	@Override
	public PathValidator isDirectory(LinkOption... options) throws IOException
	{
		isType("directory", BasicFileAttributes::isDirectory, options);
		return this;
	}

	@Override
	public PathValidator isRelative()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isAbsolute()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				PathMessages.isRelative(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Path::isAbsolute))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				PathMessages.isAbsolute(this).toString());
		}
		return this;
	}
}