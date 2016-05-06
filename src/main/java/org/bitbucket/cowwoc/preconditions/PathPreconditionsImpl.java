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
import java.util.Objects;

/**
 * Default implementation of PathPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class PathPreconditionsImpl extends AbstractObjectPreconditions<PathPreconditions, Path>
	implements PathPreconditions
{
	/**
	 * Creates new PathPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PathPreconditionsImpl(Path parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
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
				String.format("%s must refer to a file.\n" +
					"Actual: %s", name, parameter.toAbsolutePath()));
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
				String.format("%s must refer to a directory.\n" +
					"Actual: %s", name, parameter.toAbsolutePath()));
		}
		return this;
	}

	@Override
	public PathPreconditions isRelative() throws IllegalArgumentException
	{
		if (!parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must refer to a relative path.\n" +
				"Actual: %s", name, parameter));
	}

	@Override
	public PathPreconditions isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		return throwException(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.\n" +
				"Actual: %s", name, parameter));
	}

	@Override
	public PathPreconditions usingException(Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new PathPreconditionsImpl(parameter, name, exceptionOverride);
	}
}
