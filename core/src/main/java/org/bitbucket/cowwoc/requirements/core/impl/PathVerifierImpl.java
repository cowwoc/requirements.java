/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of PathVerifier.
 *
 * @author Gili Tzabari
 */
public final class PathVerifierImpl extends ObjectCapabilitiesImpl<PathVerifier, Path>
	implements PathVerifier
{
	/**
	 * Creates new PathVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PathVerifierImpl(ApplicationScope scope, Path actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public PathVerifier exists()
	{
		if (Files.exists(actual))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath())).
			build();
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw new ExceptionBuilder(config, IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isRegularFile())
		{
			throw new ExceptionBuilder(config, IllegalArgumentException.class,
				String.format("%s must refer to a file.", name)).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw new ExceptionBuilder(config, IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isDirectory())
		{
			throw new ExceptionBuilder(config, IllegalArgumentException.class,
				String.format("%s must refer to a directory.", name)).
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
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name)).
			addContext("Actual", actual).
			build();
	}
}
