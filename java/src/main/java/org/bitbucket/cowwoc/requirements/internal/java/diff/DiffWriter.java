/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.diff;

import java.util.List;

/**
 * Generates a String representing the diff of {@code Actual} and {@code Expected} Strings.
 */
public interface DiffWriter extends AutoCloseable
{
	/**
	 * @param text the text to keep in {@code Actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void keep(String text);

	/**
	 * @param text the text that needs to be inserted into {@code Actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void insert(String text);

	/**
	 * @param text the text that needs to be deleted from {@code Actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void delete(String text);

	/**
	 * @return the lines of the actual value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getActual();

	/**
	 * @return the lines to optionally display after "actual" and before "expected" (the lines are
	 *         empty if they should not be displayed)
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getMiddle();

	/**
	 * @return the lines of the expected value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getExpected();

	/**
	 * @return a padding character used to align values vertically
	 */
	String getPaddingMarker();

	@Override
	void close();
}
