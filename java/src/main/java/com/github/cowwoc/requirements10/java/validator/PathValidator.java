/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Validates the state of a {@link Path}.
 */
public interface PathValidator extends
	ValidatorComponent<PathValidator, Path>,
	ObjectComponent<PathValidator, Path>
{
	/**
	 * Ensures that the path exists.
	 * <p>
	 * The state of the path may change immediately upon returning from this method. Consequently, special care
	 * should be taken when using this method in security-sensitive applications.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the path does not exist
	 */
	PathValidator exists();

	/**
	 * Ensures that the path is absolute.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the path is relative
	 */
	PathValidator isAbsolute();

	/**
	 * Ensures that the path is relative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value refers to an absolute path
	 */
	PathValidator isRelative();

	/**
	 * Ensures that the path is a directory.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a directory
	 * @throws IOException              if an I/O error occurs while reading the file attributes (e.g., the file
	 *                                  does not exist or the user lacks the required permissions)
	 */
	PathValidator isDirectory(LinkOption... options) throws IOException;

	/**
	 * Ensures that the path is a regular file.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a file
	 * @throws IOException              if an I/O error occurs while reading the file attributes (e.g., the file
	 *                                  does not exist or the user lacks the required permissions)
	 */
	PathValidator isRegularFile(LinkOption... options) throws IOException;
}