/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Verifies preconditions of a {@link Path} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface PathPreconditions extends ObjectPreconditions<PathPreconditions, Path>
{
	/**
	 * Ensures that a path exists.
	 * <p>
	 * Note that the result of this method is immediately outdated. If this method indicates the file
	 * exists then there is no guarantee that a subsequence access will succeed. Care should be taken
	 * when using this method in security sensitive applications.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent path
	 */
	PathPreconditions exists() throws IllegalArgumentException;

	/**
	 * Ensures that a path is absolute.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to an absolute path
	 */
	PathPreconditions isAbsolute() throws IllegalArgumentException;

	/**
	 * Ensures that a path refers to a directory.
	 * <p>
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-directory path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathPreconditions isDirectory(LinkOption... options) throws IllegalArgumentException, IOException;

	/**
	 * Ensures that a path refers to a regular file.
	 * <p>
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-file path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathPreconditions isRegularFile(LinkOption... options)
		throws IllegalArgumentException, IOException;

	/**
	 * Ensures that a path is relative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to an absolute path
	 */
	PathPreconditions isRelative() throws IllegalArgumentException;
}
