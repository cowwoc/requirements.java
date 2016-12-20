/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies a {@link Path} parameter.
 *
 * @author Gili Tzabari
 */
public interface PathVerifier
	extends ObjectVerifierExtension<PathVerifier, Path>, Verifier<PathVerifier>
{
	/**
	 * Ensures that the actual value exists.
	 * <p>
	 * Note that the result of this method is immediately outdated. If this method indicates the file
	 * exists then there is no guarantee that a subsequence access will succeed. Care should be taken
	 * when using this method in security sensitive applications.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value refers to a non-existent path
	 */
	PathVerifier exists();

	/**
	 * Ensures that the actual value is absolute.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value refers to an absolute path
	 */
	PathVerifier isAbsolute();

	/**
	 * Ensures that the actual value refers to a directory.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if actual value refers to a non-existent or a non-directory path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathVerifier isDirectory(LinkOption... options) throws IOException;

	/**
	 * Ensures that the actual value refers to a regular file.
	 *
	 * @param options options indicating how symbolic links are handled
	 * @return this
	 * @throws IllegalArgumentException if actual value refers to a non-existent or a non-file path
	 * @throws IOException              if an I/O error occurs while reading the file attributes
	 */
	PathVerifier isRegularFile(LinkOption... options) throws IOException;

	/**
	 * Ensures that the actual value is relative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value refers to an absolute path
	 */
	PathVerifier isRelative();
}
