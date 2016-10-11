/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Verifies requirements of a {@link Path} parameter.
 *
 * @author Gili Tzabari
 */
public interface PathRequirements
	extends ObjectRequirementsSpi<PathRequirements, Path>, Isolatable<PathRequirements>
{
	/**
	 * Ensures that the parameter exists.
	 * <p>
	 * Note that the result of this method is immediately outdated. If this method indicates the file
	 * exists then there is no guarantee that a subsequence access will succeed. Care should be taken
	 * when using this method in security sensitive applications.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent path
	 */
	PathRequirements exists() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is absolute.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to an absolute path
	 */
	PathRequirements isAbsolute() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter refers to a directory.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-directory path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathRequirements isDirectory(LinkOption... options) throws IllegalArgumentException, IOException;

	/**
	 * Ensures that the parameter refers to a regular file.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to a non-existent or a non-file path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathRequirements isRegularFile(LinkOption... options)
		throws IllegalArgumentException, IOException;

	/**
	 * Ensures that the parameter is relative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter refers to an absolute path
	 */
	PathRequirements isRelative() throws IllegalArgumentException;
}
