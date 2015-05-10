/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

/**
 * Default implementation of PathPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class PathPreconditionsImpl extends ObjectPreconditionsImpl<PathPreconditions, Path>
	implements PathPreconditions
{
	/**
	 * Creates new PathPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	PathPreconditionsImpl(Path parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public PathPreconditions exists() throws IllegalArgumentException
	{
		if (Files.exists(parameter))
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()));
	}

	@Override
	public PathPreconditions isRegularFile(LinkOption... options)
		throws IllegalArgumentException, IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(parameter, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()),
				e);
		}
		if (!attrs.isRegularFile())
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s must refer to a file. Was: %s", name, parameter.toAbsolutePath()));
		}
		return this;
	}

	@Override
	public PathPreconditions isDirectory(LinkOption... options)
		throws IllegalArgumentException, IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(parameter, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()),
				e);
		}
		if (!attrs.isDirectory())
		{
			return throwException(IllegalArgumentException.class,
				String.format("%s must refer to a directory. Was: %s", name, parameter.toAbsolutePath()));
		}
		return this;
	}

	@Override
	public PathPreconditions isRelative()
		throws IllegalArgumentException
	{
		if (!parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must refer to a relative path. Was: %s", name, parameter));
	}

	@Override
	public PathPreconditions isAbsolute()
		throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path. Was: %s", name, parameter));
	}
}