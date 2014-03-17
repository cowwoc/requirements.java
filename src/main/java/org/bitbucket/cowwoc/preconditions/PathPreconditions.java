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

/**
 * Verifies preconditions of a Path parameter.
 * <p/>
 * @author Gili Tzabari
 */
public final class PathPreconditions extends Preconditions<PathPreconditions, Path>
{
	/**
	 * Creates new PathPreconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PathPreconditions(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name);
	}

	/**
	 * Ensures that a path exists.
	 * <p>
	 * Note that the result of this method is immediately outdated. If this method indicates the file
	 * exists then there is no guarantee that a subsequence access will succeed. Care should be taken
	 * when using this method in security sensitive applications.
	 * <p/>
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent path
	 */
	public PathPreconditions exists() throws IllegalArgumentException
	{
		if (!Files.exists(parameter))
		{
			throw new IllegalArgumentException(name + " refers to a non-existent path: " +
				parameter.toAbsolutePath());
		}
		return this;
	}

	/**
	 * Ensures that a path refers to a regular file.
	 * <p/>
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-file path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
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
			throw new IllegalArgumentException(name + " refers to a non-existent path: " +
				parameter.toAbsolutePath(), e);
		}
		if (!attrs.isRegularFile())
		{
			throw new IllegalArgumentException(name + " must refer to a file. Was: " +
				parameter.toAbsolutePath());
		}
		return this;
	}

	/**
	 * Ensures that a path refers to a directory.
	 * <p/>
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-directory path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
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
			throw new IllegalArgumentException(name + " refers to a non-existent path: " +
				parameter.toAbsolutePath(), e);
		}
		if (!attrs.isDirectory())
		{
			throw new IllegalArgumentException(name + " must refer to a directory. Was: " +
				parameter.toAbsolutePath());
		}
		return this;
	}
}
