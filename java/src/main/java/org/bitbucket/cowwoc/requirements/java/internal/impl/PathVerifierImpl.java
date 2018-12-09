/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

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
 * Default implementation of PathVerifier.
 */
public final class PathVerifierImpl extends ObjectCapabilitiesImpl<PathVerifier, Path>
	implements PathVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if {@code name} is empty
	 */
	protected PathVerifierImpl(ApplicationScope scope, String name, Path actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public PathVerifier exists()
	{
		if (Files.exists(actual))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s refers to a non-existent path", name)).
			addContext("Actual", actual.toAbsolutePath()).
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
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s refers to a non-existent path", name), e).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		if (!attrs.isRegularFile())
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
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
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s refers to a non-existent path", name), e).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		if (!attrs.isDirectory())
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
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
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name)).
			addContext("Actual", actual).
			build();
	}
}
