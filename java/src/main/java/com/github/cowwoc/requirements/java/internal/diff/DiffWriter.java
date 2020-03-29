/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.List;

/**
 * Generates the String representation of a diff between {@code actual} and {@code expected} values.
 */
public interface DiffWriter extends AutoCloseable
{
	/**
	 * Adds text that did not change. Only one line may be written at a time.
	 *
	 * @param text the text to keep in {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void writeUnchanged(String text);

	/**
	 * Adds text that is present in {@code expected} but not {@code actual}.  Only one line may be written at a
	 * time.
	 *
	 * @param text the text that needs to be inserted into {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void writeInserted(String text);

	/**
	 * Deletes text that is present in {@code actual} but not {@code expected}.  Only one line may be written
	 * at a time.
	 *
	 * @param text the text that needs to be deleted from {@code actual}
	 * @throws IllegalStateException if the writer was closed
	 */
	void writeDeleted(String text);

	/**
	 * @param length the number of characters to pad
	 * @return the (possibly decorated) text
	 */
	String decoratePadding(int length);

	/**
	 * @return the lines of the actual value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getActualLines();

	/**
	 * @return the lines to display after "actual" and before "expected" (empty lines should not be displayed)
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getMiddleLines();

	/**
	 * @return the lines of the expected value
	 * @throws IllegalStateException if the writer is open
	 */
	List<String> getExpectedLines();

	/**
	 * @return a padding character used to align values vertically
	 */
	String getPaddingMarker();

	@Override
	void close();
}
