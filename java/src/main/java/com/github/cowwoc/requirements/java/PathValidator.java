/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Validates the requirements of a {@link Path}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
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
	 * @return the updated validator
	 */
	PathValidator exists();

	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return the updated validator
	 */
	PathValidator isAbsolute();

	/**
	 * Ensures that the actual value refers to a directory.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return the updated validator
	 * @throws NullPointerException if {@code options} is null
	 */
	PathValidator isDirectory(LinkOption... options);

	/**
	 * Ensures that the actual value refers to a regular file.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return the updated validator
	 * @throws NullPointerException if {@code options} is null
	 */
	PathValidator isRegularFile(LinkOption... options);

	/**
	 * Ensures that the actual value is relative.
	 *
	 * @return the updated validator
	 */
	PathValidator isRelative();
}
