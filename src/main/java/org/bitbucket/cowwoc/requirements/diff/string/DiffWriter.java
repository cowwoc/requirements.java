/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.annotations.Beta;
import java.util.Optional;

/**
 * Generates a String representing the diff of {@code actual} and {@code expected} Strings.
 *
 * @author Gili Tzabari
 */
@Beta
public interface DiffWriter extends AutoCloseable
{
	/**
	 * @param text the text that is present in both variables
	 */
	void unchanged(String text);

	/**
	 * @param text the text that needs to be inserted into {@code actual}
	 */
	void inserted(String text);

	/**
	 * @param text the text that needs to be deleted from {@code actual}
	 */
	void deleted(String text);

	/**
	 * @return the actual value
	 */
	String getActual();

	/**
	 * @return the expected value
	 */
	String getExpected();

	/**
	 * @return a line to optionally display after "actual" and before "expected"
	 */
	Optional<String> getMiddle();

	/**
	 * @return the String produced by the writer
	 */
	@Override
	String toString();
}
