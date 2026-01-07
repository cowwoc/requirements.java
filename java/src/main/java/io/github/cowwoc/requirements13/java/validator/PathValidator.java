/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator;

import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;

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

	/**
	 * Ensures that the path exists and the JVM has sufficient privileges to execute it.
	 * <p>
	 * This does not imply that the path is an executable file.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the path does not exist, or the JVM does not have sufficient
	 *                                  privileges to execute it
	 * @see Files#isExecutable(Path)
	 */
	PathValidator isExecutable();

	/**
	 * Ensures that the path is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the path is not empty
	 */
	PathValidator isEmpty();

	/**
	 * Ensures that the path is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the path is empty
	 */
	PathValidator isNotEmpty();

	/**
	 * Ensures that the path contains another path.
	 *
	 * @param expected the other path
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} is null
	 * @throws IllegalArgumentException if the path does not contain {@code expected}
	 */
	PathValidator contains(Path expected);

	/**
	 * Ensures that the path contains another path.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the other path
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} is null
	 * @throws IllegalArgumentException if the path does not contain {@code expected}
	 */
	PathValidator contains(Path expected, String name);

	/**
	 * Ensures that the path does not contain {@code unwanted}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted path
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the path contains {@code unwanted}
	 */
	PathValidator doesNotContain(Path unwanted);

	/**
	 * Ensures that the path does not contain {@code unwanted}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted path
	 * @param name     the name of the path
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path contains {@code unwanted}</li>
	 *                                  </ul>
	 */
	PathValidator doesNotContain(Path unwanted, String name);

	/**
	 * Ensures that the path contains the same paths as {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>the path is missing any path in {@code expected}</li>
	 *                                    <li>the path contains any path that is not in {@code expected}</li>
	 *                                  </ul>
	 */
	PathValidator containsExactly(Collection<Path> expected);

	/**
	 * Ensures that the path consists of the same paths as {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @param name     the name of the expected paths
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path contains any path that is not in {@code expected}</li>
	 *                                  </ul>
	 */
	PathValidator containsExactly(Collection<Path> expected, String name);

	/**
	 * Ensures that the path and {@code unwanted} consist of different paths.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the path consists of the same paths as {@code unwanted}
	 */
	PathValidator doesNotContainExactly(Collection<Path> unwanted);

	/**
	 * Ensures that the path and {@code unwanted} consist of different paths.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path consists of the same paths as {@code unwanted}</li>
	 *                                  </ul>
	 */
	PathValidator doesNotContainExactly(Collection<Path> unwanted, String name);

	/**
	 * Ensures that the path contains at least one path in {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the path does not contain any path in {@code expected}
	 */
	PathValidator containsAny(Collection<Path> expected);

	/**
	 * Ensures that the path contains at least one path in {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @param name     the name of the expected paths
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path does not contain any path in {@code expected}</li>
	 *                                  </ul>
	 */
	PathValidator containsAny(Collection<Path> expected, String name);

	/**
	 * Ensures that the path does not contain any of the paths in {@code unwanted}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the path contains any of the paths in {@code unwanted}
	 */
	PathValidator doesNotContainAny(Collection<Path> unwanted);

	/**
	 * Ensures that the path does not contain any of the paths in {@code unwanted}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted paths
	 * @param name     the name of the unwanted paths
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path contains any of the paths in {@code unwanted}</li>
	 *                                  </ul>
	 */
	PathValidator doesNotContainAny(Collection<Path> unwanted, String name);

	/**
	 * Ensures that the path contains all the paths in {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the path does not contain all the paths in {@code expected}
	 */
	PathValidator containsAll(Collection<Path> expected);

	/**
	 * Ensures that the path contains all the paths in {@code expected}.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param expected the desired paths
	 * @param name     the name of the expected paths
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path does not contain all the paths in {@code expected}</li>
	 *                                  </ul>
	 */
	PathValidator containsAll(Collection<Path> expected, String name);

	/**
	 * Allows the path to contain some, but not all, paths from another collection.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted paths
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the path contains all the paths in {@code unwanted}
	 */
	PathValidator doesNotContainAll(Collection<Path> unwanted);

	/**
	 * Allows the path to contain some, but not all, paths from another collection.
	 * <p>
	 * Note: This method only verifies direct (one-level deep) descendants and does not perform a recursive
	 * check.
	 *
	 * @param unwanted the unwanted paths
	 * @param name     the name of the unwanted paths
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the path contains all the paths in {@code unwanted}</li>
	 *                                  </ul>
	 */
	PathValidator doesNotContainAll(Collection<Path> unwanted, String name);
}