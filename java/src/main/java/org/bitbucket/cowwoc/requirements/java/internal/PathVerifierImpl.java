/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PathVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Default implementation of {@code PathVerifier}.
 */
public final class PathVerifierImpl extends ObjectCapabilitiesImpl<PathVerifier, Path>
	implements PathVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public PathVerifierImpl(ApplicationScope scope, String name, Path actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public PathVerifier exists()
	{
		if (Files.exists(actual))
			return this;
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " refers to a non-existent path.").
			addContext("Actual", actual.toAbsolutePath()).
			build();
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs = getFileAttributes();
		if (!attrs.isRegularFile())
		{
			throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
				name + " must refer to a file.").
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		return this;
	}

	/**
	 * @param options options indicating how symbolic links are handled
	 * @return the file attributes of {@code actual}
	 * @throws IllegalArgumentException if {@code actual} does not exist
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	private BasicFileAttributes getFileAttributes(LinkOption... options) throws IOException
	{
		try
		{
			return Files.readAttributes(actual, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
				name + " refers to a non-existent path").
				setCause(e).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs = getFileAttributes();
		if (!attrs.isDirectory())
		{
			throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
				name + " must refer to a directory.").
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathVerifier isRelative()
	{
		if (!actual.isAbsolute())
			return this;
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must refer to a relative path.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must refer to an absolute path.").
			addContext("Actual", actual).
			build();
	}
}
