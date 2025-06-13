/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.internal.message.diff;

import java.util.List;

/**
 * Generates the String representation of a diff between {@code actual} and {@code expected} values.
 */
public interface DiffWriter
{
	/**
	 * Adds text that is equal in {@code expected} and {@code actual}.
	 *
	 * @param text the text to keep in {@code actual}
	 * @throws IllegalStateException if the writer was already flushed
	 */
	void writeEqual(String text);

	/**
	 * Deletes text that is present in {@code actual} but not {@code expected}.
	 *
	 * @param text the text that needs to be deleted from {@code actual}
	 * @throws IllegalStateException if the writer was already flushed
	 */
	void writeDeleted(String text);

	/**
	 * Adds text that is present in {@code expected} but not {@code actual}.
	 *
	 * @param text the text that needs to be inserted into {@code actual}
	 * @throws IllegalStateException if the writer was already flushed
	 */
	void writeInserted(String text);

	/**
	 * @return the lines of the actual value
	 * @throws IllegalStateException if the writer was already flushed
	 */
	List<String> getActualLines();

	/**
	 * @return the lines to display after "actual" and before "expected" (empty lines should not be displayed)
	 * @throws IllegalStateException if the writer was already flushed
	 */
	List<String> getDiffLines();

	/**
	 * @return the lines of the expected value
	 * @throws IllegalStateException if the writer was already flushed
	 */
	List<String> getExpectedLines();

	/**
	 * @return a list that indicates whether the actual and expected values are equal on each line
	 * @throws IllegalStateException if the writer was already flushed
	 */
	List<Boolean> getEqualLines();

	/**
	 * @return a padding character used to align values vertically
	 */
	String getPaddingMarker();

	/**
	 * Flushes the writer's output.
	 */
	void flush();
}