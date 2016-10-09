/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import java.util.List;

/**
 * Generates a String representing the diff of {@code actual} and {@code expected} Strings.
 *
 * @author Gili Tzabari
 */
interface DiffWriter extends AutoCloseable
{
	/**
	 * @param text the text to keep in {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void keep(String text) throws IllegalStateException;

	/**
	 * @param text the text that needs to be inserted into {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void insert(String text) throws IllegalStateException;

	/**
	 * @param text the text that needs to be deleted from {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void delete(String text) throws IllegalStateException;

	/**
	 * @return the lines of the actual value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getActual() throws IllegalStateException;

	/**
	 * @return the lines of the expected value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getExpected() throws IllegalStateException;

	/**
	 * @return the lines to optionally display after "actual" and before "expected" (the lines are
	 *         empty if they should not be displayed)
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getMiddle() throws IllegalStateException;

	@Override
	void close();
}
