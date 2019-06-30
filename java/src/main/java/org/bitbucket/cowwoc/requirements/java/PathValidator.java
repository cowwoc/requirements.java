/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Validates the requirements of a {@link Path}.
 */
public interface PathValidator extends ExtensibleObjectValidator<PathValidator, Path>
{
	/**
	 * Ensures that the actual value exists.
	 * <p>
	 * Note that the result of this method is immediately outdated. If this method indicates the file
	 * exists then there is no guarantee that a subsequence access will succeed. Care should be taken
	 * when using this method in security sensitive applications.
	 *
	 * @return this
	 */
	PathValidator exists();

	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return this
	 */
	PathValidator isAbsolute();

	/**
	 * Ensures that the actual value refers to a directory.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 */
	PathValidator isDirectory(LinkOption... options);

	/**
	 * Ensures that the actual value refers to a regular file.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 */
	PathValidator isRegularFile(LinkOption... options);

	/**
	 * Ensures that the actual value is relative.
	 *
	 * @return this
	 */
	PathValidator isRelative();
}
